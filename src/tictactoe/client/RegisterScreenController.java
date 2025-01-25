package tictactoe.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import network.requests.RegisterRequest;
import network.responses.RegisterResponse;
import network.responses.FailRegisterResponse;
import network.responses.SuccessRegisterResponse;
import tictactoe.client.ui.UiUtils;

public class RegisterScreenController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerbtn;
    @FXML
    private Button backbtn;

    private PlayerSocket playerSocket;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerSocket = PlayerSocket.getInstance();
    }

    @FXML
    private void handleRegister() {
        String username = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            UiUtils.showValidationAlert("Required fields are empty!");
            return;
        }

        if (!isValidEmail(email)) {
            UiUtils.showValidationAlert("Invalid email format!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            UiUtils.showValidationAlert("Passwords do not match!");
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(username, password,email);
        playerSocket.sendRequest(registerRequest);

        // Wait for the response
        System.out.println("sent registeration request and waiting for the response");
        RegisterResponse registerResponse = (RegisterResponse) playerSocket.receiveResponse();
        System.out.println("Register Response has come");  
        
        if (registerResponse instanceof SuccessRegisterResponse) {
            SuccessRegisterResponse successResponse = (SuccessRegisterResponse) registerResponse;
            System.out.println("Registration successful for user: " + successResponse.getUsername());
            navigateToScreen("LoginScreen.fxml", registerbtn);
        } else if (registerResponse instanceof FailRegisterResponse) {
            FailRegisterResponse failResponse = (FailRegisterResponse) registerResponse;
            UiUtils.showValidationAlert(failResponse.getMessage());
        } else {
            UiUtils.showValidationAlert("Unexpected response from the server.");
        }
    }

    @FXML
    private void handleBack() {
        navigateToScreen("LoginScreen.fxml", backbtn);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
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