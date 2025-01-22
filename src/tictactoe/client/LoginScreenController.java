package tictactoe.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.requests.LoginRequest;
import network.responses.LoginResponse;
import network.requests.RegisterRequest;
import network.responses.RegisterResponse;
import network.responses.SuccessLoginResponse;
import network.responses.FailLoginResponse;
import network.responses.Response;
import tictactoe.client.ui.UiUtils;

/**
 * FXML Controller class
 *
 * @author Laptop World
 */
public class LoginScreenController implements Initializable {

    @FXML
    private Button loginbtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button registerbtn;
    @FXML
    private Button backBtn;
    private PlayerSocket playerSocket;

    /**
     * Initializes the controller class.
     */

    /*@FXML
    void handleRegister() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) registerbtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }*/
    void handleLogin() {
        System.out.println("Login button clicked");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        playerSocket = playerSocket.getInstance();
        registerbtn.setOnAction((event) -> {
            navigatePage("RegisterScreen.fxml", registerbtn);

        });

////        loginbtn.setOnAction((event) -> {
////            if (nameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
////                UiUtils.showValidationAlert("Requiered field is empty!");
////            } else {
////                LoginRequest loginRequest = new LoginRequest(nameField.getText(), passwordField.getText());
////                playerSocket.sendRequest(loginRequest);
////                Response response = playerSocket.receiveResponse();
////                if (response instanceof LoginResponse) {
////                    LoginResponse loginResponse = (LoginResponse) response;
////                    if (loginResponse instanceof SuccessLoginResponse) {
////                        SuccessLoginResponse successLoginResponse = (SuccessLoginResponse) loginResponse;
////                        String username = successLoginResponse.getUsername();
////                        int score = successLoginResponse.getScore();
////
////                        navigatePage("AvailablePlayersScreen.fxml", loginbtn);
////
////                    } else if (loginResponse instanceof FailLoginResponse) {
////                        FailLoginResponse failLoginResponse = (FailLoginResponse) loginResponse;
////                        failLoginResponse.getMessage();
////
////                    }
////                }
////
////            }
//        });
        loginbtn.setOnAction((event) -> {
            if (nameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                UiUtils.showValidationAlert("Required field is empty!");
            } else {
                try {
                    LoginRequest loginRequest = new LoginRequest(nameField.getText(), passwordField.getText());
                    playerSocket.sendRequest(loginRequest);
                    Response response = playerSocket.receiveResponse();

                    if (response == null) {
                        UiUtils.showValidationAlert("Failed to receive a response from the server.");
                        return;
                    }

                    if (response instanceof LoginResponse) {
                        LoginResponse loginResponse = (LoginResponse) response;
                        if (loginResponse instanceof SuccessLoginResponse) {
                            SuccessLoginResponse successLoginResponse = (SuccessLoginResponse) loginResponse;
                            String username = successLoginResponse.getUsername();
                            int score = successLoginResponse.getScore();
                            navigatePage("AvailablePlayersScreen.fxml", loginbtn);
                        } else if (loginResponse instanceof FailLoginResponse) {
                            FailLoginResponse failLoginResponse = (FailLoginResponse) loginResponse;
                            UiUtils.showValidationAlert(failLoginResponse.getMessage());
                        }
                    } else {
                        UiUtils.showValidationAlert("Unexpected response type received.");
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Print the stack trace for debugging
                    UiUtils.showValidationAlert("An error occurred while processing the login response.");
                }
            }
        });

        backBtn.setOnAction((event) -> {
            navigatePage("StartOptionsScreen.fxml", backBtn);

        });

    }

    private void navigatePage(String fxmlFile, Button button) {
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

