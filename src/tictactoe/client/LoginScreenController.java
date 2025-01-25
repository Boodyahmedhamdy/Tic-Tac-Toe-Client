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
import javax.sound.midi.ControllerEventListener;
import network.requests.LoginRequest;
import network.responses.LoginResponse;
import network.responses.SuccessLoginResponse;
import network.responses.FailLoginResponse;
import tictactoe.client.ui.NavigationUtils;
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

    private static PlayerInfo playerInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerSocket = PlayerSocket.getInstance();
        playerSocket.setLoginScreenController(this);
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

        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        // Wait for the response
        System.out.println("Waiting for LoginResponse...");
    }

    public void onSuccessLogin(SuccessLoginResponse successResponse) {
        playerInfo = PlayerInfo.getInstance();
        playerInfo.setUserName(successResponse.getUsername());
        playerInfo.setRank(successResponse.getScore());
        System.out.println("Login successful for user: " + successResponse.getUsername());

        if (loginbtn != null) {
            System.out.println("loginbtn is not null. Navigating to the next screen...");
            navigateToScreen("AvailablePlayersScreen.fxml", loginbtn);
        } else {
            System.err.println("loginbtn is null. Cannot navigate to the next screen.");
        }
    }

    public void onFailLogin(FailLoginResponse failResponse) {
        UiUtils.showValidationAlert(failResponse.getMessage());
    }

    @FXML
    private void handleRegister() {
        navigateToScreen("RegisterScreen.fxml", registerbtn);
    }

    public static void navigateToScreen(String fxmlFile, Button button) {
        if (button == null) {
            System.err.println("Button is null. Cannot navigate to screen.");
            return;
        }
        try {
            URL url = LoginScreenController.class.getResource(fxmlFile);
            if (url == null) {
                System.err.println("FXML file not found: " + fxmlFile);
                return;
            }
            System.out.println("Loading FXML from: " + url);
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
