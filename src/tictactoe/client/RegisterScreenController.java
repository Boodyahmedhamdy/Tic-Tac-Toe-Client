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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         backbtn.setOnAction((event) -> {
                navigatePage("LoginScreen.fxml",backbtn);
    });        
        registerbtn.setOnAction((event)->{
            
            if (emailField.getText().isEmpty()||nameField.getText().isEmpty() || passwordField.getText().isEmpty()||confirmPasswordField.getText().isEmpty()) {

         UiUtils.showValidationAlert("Requiered field is empty!");
           }
            else if(!isValid(emailField.getText())){
                UiUtils.showValidationAlert("Invalid Email!");
           }
            else if(!passwordField.getText().equals(confirmPasswordField.getText())){
                UiUtils.showValidationAlert("Please confirm your password!");

           }
            else{
                navigatePage("AvailablePlayersScreen.fxml",registerbtn);
            }
        });
    } 
    
     public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();
    }
     
     void navigatePage(String sentence ,Button button){
           try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sentence));
        Parent root = loader.load();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
     }
   
    
}
