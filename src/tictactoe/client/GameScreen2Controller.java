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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class GameScreen2Controller implements Initializable {

    @FXML
    private ImageView back;
    @FXML
    private ImageView player1_symbol;
    @FXML
    private Label player1_name;
    @FXML
    private Label player1_score;
    @FXML
    private Label player2_score;
    @FXML
    private ImageView player2_symbol;
    @FXML
    private Label player2_name;
    @FXML
    private ImageView symbol00;
    @FXML
    private ImageView symbol01;
    @FXML
    private ImageView symbol02;
    @FXML
    private ImageView symbol10;
    @FXML
    private ImageView symbol11;
    @FXML
    private ImageView symbol12;
    @FXML
    private ImageView symbol20;
    @FXML
    private ImageView symbol21;
    @FXML
    private ImageView symbol22;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void handleButtonClick() {
        
    }
    
}
