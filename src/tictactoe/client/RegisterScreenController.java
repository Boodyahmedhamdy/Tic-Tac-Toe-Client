/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        try {         
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backbtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
         
        registerbtn.setOnAction((event)->{
            if (emailField.getText().isEmpty()||nameField.getText().isEmpty() || passwordField.getText().isEmpty()||confirmPasswordField.getText().isEmpty()) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Requiered field is empty!");
        a.showAndWait();
           }else if(!isValid(emailField.getText())){
               Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Invalid Email!");
        a.showAndWait();
           }
            else if(!passwordField.getText().equals(confirmPasswordField.getText())){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Please confirm your password!");
        a.showAndWait();
           }
            else{
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AvailablePlayersScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) registerbtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }}
        });
    } 
    
     public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();
    }
    
}
