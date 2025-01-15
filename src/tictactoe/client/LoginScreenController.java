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

        registerbtn.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) registerbtn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loginbtn.setOnAction((event) -> {
            if (nameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.getDialogPane().getStylesheets().add(getClass().getResource("ui/styles/Alert_Dialogs_Style.css").toExternalForm());
                a.setContentText("Requiered field is empty!");
                a.showAndWait();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AvailablePlayersScreen.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) loginbtn.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        backBtn.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StartOptionsScreen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) backBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
