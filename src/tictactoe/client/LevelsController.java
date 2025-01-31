/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class LevelsController implements Initializable {

    @FXML
    private Label gameTitle;
   
    @FXML
    private Button backBtn;
    @FXML
    private Button Easy;
    @FXML
    private Button Meduim;
    @FXML
    private Button Hard;

    /**
     * Initializes the controller class.
     */
    
    private void setupButtonActions() {
        Easy.setOnAction(event -> handleEasy());
        Meduim.setOnAction(event -> handleMeduim());
        Hard.setOnAction(event -> handleHard());
        backBtn.setOnAction(event -> handlePreviousRecords());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         setupButtonActions();
    }    

    
    
    private void handlePreviousRecords() {
        try {
            
            navigateToScreen("StartOptionsScreen.fxml", backBtn);
        } catch (IOException ex) {
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleEasy() {
        System.out.println("Level: Easy");
        try {
           
            GameScreenWithAIController.difficulty="Easy";
            navigateToScreen("gameScreenWithAI.fxml", Easy);
        } catch (IOException ex) {
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleMeduim() {
        
        System.out.println("Level: Meduim");
        try {
           
            GameScreenWithAIController.difficulty="Medium";
            navigateToScreen("gameScreenWithAI.fxml", Meduim);
        } catch (IOException ex) {
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleHard() {
        System.out.println("Level: Hard");
        try {
           
            GameScreenWithAIController.difficulty="Hard";
            navigateToScreen("gameScreenWithAI.fxml", Hard);
        } catch (IOException ex) {
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void navigateToScreen(String fxmlFile, Button b) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) b.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}