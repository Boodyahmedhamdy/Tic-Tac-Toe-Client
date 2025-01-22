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
import network.NetworkAccessLayer;
import network.actions.SignOutAction;
import network.requests.StartGameRequest;
import network.responses.AcceptStartGameResponse;
import network.responses.RefuseStartGameResponse;
import network.responses.StartGameResponse;
import tictactoe.client.ui.UiUtils;
import tictactoe.client.ui.states.UserListItemUiState;

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
                textErrorMessage.setText("Error navigating to StartOptionsScreen: " + ex.getMessage());
            }
        });

        // Add dummy players for testing
        lvAvailablePlayers.getItems().addAll(
                new UserListItemUiState("boody", 33),
                new UserListItemUiState("Ahmed", 323)
        );

        // Add listener for player selection
        lvAvailablePlayers.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends UserListItemUiState> observable, UserListItemUiState oldValue, UserListItemUiState newValue) -> {
                    if (newValue != null) {
                        onClickOnPlayer(newValue);
                    }
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
            NetworkAccessLayer.sendSignOutAction(signOutAction);
            navigateToScreen("StartOptionsScreen.fxml", btnSignOut);
        } catch (IOException ex) {
            Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            textErrorMessage.setText("Error signing out: " + ex.getMessage());
        }
    }

    @FXML
    private void onClickOnPlayer(UserListItemUiState playerUiState) {
        try {
            StartGameRequest startGameRequest = new StartGameRequest(playerUiState.getUsername());
            NetworkAccessLayer.sendStartGameRequest(startGameRequest);
            System.out.println("Start game request sent to: " + playerUiState.getUsername());
        } catch (IOException ex) {
            Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            textErrorMessage.setText("Error sending game request: " + ex.getMessage());
        }
    }

    /**
     * Called when a start game request is received from another player via the server.
     */
    private void onReceiveStartGameRequest(StartGameRequest startGameRequest) {
        UiUtils.showReplayAlert(
                startGameRequest.getUsername() + " wants to have a game with you. Do you agree?",
                () -> {
                    try {
                        // On "Yes"
                        NetworkAccessLayer.sendStartGameResponse(new AcceptStartGameResponse(startGameRequest.getUsername()));
                        navigateToScreen("gameScreen.fxml", btnSignOut); // Navigate to the game screen
                    } catch (IOException ex) {
                        Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        textErrorMessage.setText("Error accepting game request: " + ex.getMessage());
                    }
                },
                () -> {
                    try {
                        // On "No"
                        NetworkAccessLayer.sendStartGameResponse(new RefuseStartGameResponse(startGameRequest.getUsername()));
                    } catch (IOException ex) {
                        Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        textErrorMessage.setText("Error refusing game request: " + ex.getMessage());
                    }
                },
                () -> {
                    try {
                        // On "Close"
                        NetworkAccessLayer.sendStartGameResponse(new RefuseStartGameResponse(startGameRequest.getUsername()));
                    } catch (IOException ex) {
                        Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        textErrorMessage.setText("Error handling game request: " + ex.getMessage());
                    }
                }
        );
    }

    /**
     * Called when a start game response is received from another player via the server.
     */
    private void onReceiveStartGameResponse(StartGameResponse startGameResponse) {
        if (startGameResponse instanceof AcceptStartGameResponse) {
            // Handle acceptance (e.g., navigate to the game screen)
            try {
                navigateToScreen("gameScreen.fxml", btnSignOut);
            } catch (IOException ex) {
                Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                textErrorMessage.setText("Error navigating to game screen: " + ex.getMessage());
            }
        } else if (startGameResponse instanceof RefuseStartGameResponse) {
            // Handle refusal
            UiUtils.showValidationAlert("Your invitation to " + startGameResponse.getUsername() + " was refused.");
        }
    }
}