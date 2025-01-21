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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.requests.RegisterRequest;
import network.responses.RegisterResponse;
import network.responses.FailRegisterResponse;
import network.responses.Response;
import network.responses.SuccessRegisterResponse;
import tictactoe.client.ui.UiUtils;

/**
 * FXML Controller class
 *
 * @author Laptop World
 */
public class RegisterScreenController implements Initializable {

    @FXML
    private Button backbtn;
    @FXML
    private Button registerbtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    
    private PlayerSocket playerSocket;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.playerSocket =  PlayerSocket.getInstance();
        //this.playerSocket.connect(new InetSocketAddress("localhost", 12345), 1000);  // Example for connecting

        // Back button action to navigate back to Login page
        backbtn.setOnAction((event) -> {
            navigatePage("LoginScreen.fxml", backbtn);
        });

        // Register button action to register a new user
        registerbtn.setOnAction((event) -> {
            handleRegister();
        });
    }

    private void handleRegister() {
        String username = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            UiUtils.showValidationAlert("Required field is empty!");
        } else if (!isValid(email)) {
            UiUtils.showValidationAlert("Invalid Email!");
        } else if (!password.equals(confirmPassword)) {
            UiUtils.showValidationAlert("Passwords do not match!");
        } else {
            // Send register request to the server
            RegisterRequest registerRequest = new RegisterRequest(username, email, password);
            playerSocket.sendRequest(registerRequest);
            Response response = playerSocket.receiveResponse();
            if(response instanceof RegisterResponse){
                RegisterResponse registerResponse = (RegisterResponse) response;
           if (registerResponse instanceof SuccessRegisterResponse ) {
                SuccessRegisterResponse successRegisterResponse = (SuccessRegisterResponse) registerResponse;
                successRegisterResponse.getUsername();
                successRegisterResponse.getScore();
                   navigatePage("AvailablePlayersScreen.fxml", registerbtn);
                    
            }else if (registerResponse instanceof  FailRegisterResponse) {
                FailRegisterResponse failRegisterResponse =(FailRegisterResponse) registerResponse;   
                failRegisterResponse.getMessage();
                }
            }
            
        }
    }

    private boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();
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