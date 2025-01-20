package tictactoe.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.NetworkAcessLayer;
import network.actions.SignOutAction;
import network.requests.StartGameRequest;
import network.responses.AcceptStartGameResposne;
import network.responses.RefuseStartGameResponse;
import network.responses.StartGameResponse;
import tictactoe.client.ui.UiUtils;
import tictactoe.client.ui.states.UserListItemUiState;

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
    private ListView<UserListItemUiState> lvAvailablePlayers;
    @FXML
    private Text textErrorMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSignOut.setOnAction((event) -> {
            try {
                navigateToScreen("StartOptionsScreen.fxml", btnSignOut);
            } catch (IOException ex) {
                Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        lvAvailablePlayers.getItems().addAll(
                new UserListItemUiState("boody", 33),
                new UserListItemUiState("Ahmed", 323)
        );

        lvAvailablePlayers
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends UserListItemUiState> observable, UserListItemUiState oldValue, UserListItemUiState newValue) -> {
                    System.out.println(lvAvailablePlayers.getSelectionModel().getSelectedItem());
                    onClickOnPlayer(lvAvailablePlayers.getSelectionModel().getSelectedItem());
                });

    }

    private void navigateToScreen(String fxmlFile, Button b) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) b.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @FXML
    private void onClickSignOut(ActionEvent event) {
        try {
            SignOutAction signOutAction = new SignOutAction(textPlayerUserName.getText());
            NetworkAcessLayer.sendSignOutAction(signOutAction);
        } catch (IOException ex) {
            Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            textErrorMessage.setText(ex.getMessage());
        }
    }
    
    @FXML
    private void onClickOnPlayer(UserListItemUiState playerUiState) {
        try {
            StartGameRequest startGameRequest = new StartGameRequest(playerUiState.username);
            NetworkAcessLayer.sendStartGameRequest(startGameRequest);
        } catch (IOException ex) {
            Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            textErrorMessage.setText(ex.getMessage());
        }
    }
    
    /**
     * called when a start game request is sent to the client from another player
     * via the server
    */
    private void onReciveStartGameRequest(StartGameRequest startGameRequest) {
        UiUtils.showReplayAlert(startGameRequest.getUsername() + " wants to have a game with you, Do you Agree ? ",
                () -> { 
                    try {
                        // on yes
                        NetworkAcessLayer.sendStartGameResponse(
                                new AcceptStartGameResposne(startGameRequest.getUsername())
                        );
                    } catch (IOException ex) {
                        Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        textErrorMessage.setText(ex.getMessage());
                    }
                
                }, () -> {
                    try {
                        // on No
                        NetworkAcessLayer.sendStartGameResponse(
                                new RefuseStartGameResponse(startGameRequest.getUsername())
                        );
                    } catch (IOException ex) {
                        Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        textErrorMessage.setText(ex.getMessage());
                    }
                    
                }, () -> {
                    try {
                        // on close
                        NetworkAcessLayer.sendStartGameResponse(
                                new RefuseStartGameResponse(startGameRequest.getUsername())
                        );
                    } catch (IOException ex) {
                        Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        textErrorMessage.setText(ex.getMessage());
                    }
                    
                });
    }
    
    
    /**
     * to be called when user gets a response sent by another user
     * via server
    */
    private void onRecieveStartGameResponse(StartGameResponse startGameResponse) {
        if(startGameResponse instanceof AcceptStartGameResposne) {
            // do things related to acceptence like going to the game screen
            
            return;
        }  
        if(startGameResponse instanceof RefuseStartGameResponse) {
            // show user error dialog t
            
            UiUtils.showValidationAlert(
                    "your invitation to " + startGameResponse.getUsername() + " was refuesed"
            );
            return;
        }
    }

}
