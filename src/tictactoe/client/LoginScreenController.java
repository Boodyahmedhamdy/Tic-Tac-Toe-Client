package tictactoe.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import network.requests.LoginRequest;
import network.responses.LoginResponse;
import network.responses.SuccessLoginResponse;
import network.responses.FailLoginResponse;
import tictactoe.client.ui.UiUtils;

public class LoginScreenController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginbtn;
    @FXML
    private Button registerbtn;

    private PlayerSocket playerSocket;
    
    private PlayerInfo playerInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerSocket = PlayerSocket.getInstance();
    }

    @FXML
    private void handleLogin() {
        String username = nameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            UiUtils.showValidationAlert("Required fields are empty!");
            return;
        }

        // Send login request to the server
        LoginRequest loginRequest = new LoginRequest(username, password);
        System.out.println("Sending LoginRequest for username: " + username);
        playerSocket.sendRequest(loginRequest);

        // Wait for the response
        System.out.println("Waiting for LoginResponse...");
        LoginResponse loginResponse = (LoginResponse) playerSocket.receiveResponse();

        if (loginResponse instanceof SuccessLoginResponse) {
            SuccessLoginResponse successResponse = (SuccessLoginResponse) loginResponse;
            playerInfo = PlayerInfo.getInstance();
            playerInfo.setUserName(successResponse.getUsername());
            playerInfo.setRank(successResponse.getScore());
            System.out.println("Login successful for user: " + successResponse.getUsername());
            AvailablePlayersScreenController.runListeningThreadHere = true;
            navigateToScreen("AvailablePlayersScreen.fxml", loginbtn);
        } else if (loginResponse instanceof FailLoginResponse) {
            FailLoginResponse failResponse = (FailLoginResponse) loginResponse;
            UiUtils.showValidationAlert(failResponse.getMessage());
        } else {
            UiUtils.showValidationAlert("Unexpected response from the server.");
        }
    }

    @FXML
    private void handleRegister() {
        navigateToScreen("RegisterScreen.fxml", registerbtn);
    }

    private void navigateToScreen(String fxmlFile, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
