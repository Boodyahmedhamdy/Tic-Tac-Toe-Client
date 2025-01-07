/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Laptop World
 */
public class LoginScreenController implements Initializable {

    @FXML
    private Button signbtn;
    
    @FXML
    private Button loginbtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;

    /**
     * Initializes the controller class.
     */
    @FXML
    void wellcom(){
        System.out.println("tictactoe.client.LoginScreenController.wellcom()");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
