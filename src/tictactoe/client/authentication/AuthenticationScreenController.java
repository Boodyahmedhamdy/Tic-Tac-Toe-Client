/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.authentication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AuthenticationScreenController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;

    /**
     * Initializes the controller class.
     */
    @FXML
    void handleLoginButton(ActionEvent event) {
        System.out.println("Login button clicked");
    }
    
    @FXML
    void handleRegisterButton(ActionEvent event) {
        System.out.println("Register Button CLicked");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
