/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AvailablePlayersScreenController implements Initializable {

    @FXML
    private Text textPlayerUserName;
    @FXML
    private Text textPlayerScore;
    @FXML
    private Button btnSignOut;
    @FXML
    private ListView<String> lvAvailablePlayers;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvAvailablePlayers.getItems().addAll("boody", "Ahmed", "hamdy", "hassan");
        
        lvAvailablePlayers
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    System.out.println( lvAvailablePlayers.getSelectionModel().getSelectedItem() );
                });
        
    }    
    
}
