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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelrahman_Elshreif
 */
public class PreviousRecordsChooseOptionsScreenController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private ImageView logo;
    @FXML
    private Label gameTitle;
    @FXML
    private Button onlineRecordsBtn;
    @FXML
    private Button offlineRecordsBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToPrevScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartOptionsScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PreviousRecordsChooseOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToOnlineRecords(ActionEvent event) {
    }

    @FXML
    private void goToOfflineRecords(ActionEvent event) {
    }

}
