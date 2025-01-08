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
    private Button loginbtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button registerbtn;

    /**
     * Initializes the controller class.
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         registerbtn.setOnAction((event) -> {
            System.out.println("HHHHHHHHH");
        });
        // TODO
    }    
    
}
