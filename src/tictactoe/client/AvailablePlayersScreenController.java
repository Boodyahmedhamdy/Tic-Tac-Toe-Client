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
import network.actions.SignOutAction;
import network.requests.Request;
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
    
    PlayerSocket playerSocket;

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
        playerSocket = PlayerSocket.getInstance();

        // Add dummy players for testing
        lvAvailablePlayers.getItems().addAll(
                new UserListItemUiState("player1", 33),
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
            System.out.println("Sending SignOut Request");
            SignOutAction signOutAction = new SignOutAction(textPlayerUserName.getText());
//            playerSocket.sendRequest(signOutAction);
//            NetworkAccessLayer.sendSignOutAction(signOutAction);
            navigateToScreen("StartOptionsScreen.fxml", btnSignOut);
        } catch (IOException ex) {
            Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            textErrorMessage.setText("Error signing out: " + ex.getMessage());
        }
    }

    @FXML
    private void onClickOnPlayer(UserListItemUiState playerUiState) {
        
        System.out.println("sending Start game request sent to: " + playerUiState.getUsername());
        StartGameRequest startGameRequest = new StartGameRequest(playerUiState.getUsername());
        playerSocket.sendRequest(startGameRequest);
        System.out.println("Start game request sent to: " + playerUiState.getUsername());

        // reciving process
        System.out.println("waiting for StartGameRequest to come....");
        Request request = playerSocket.receiveRequest();
        System.out.println("StartGameRequest just arrived with type" + request.getClass().getSimpleName());
        if(request instanceof StartGameRequest) {
            onReceiveStartGameRequest((StartGameRequest) request);
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
                        AcceptStartGameResponse response = new AcceptStartGameResponse(startGameRequest.getUsername());
                        playerSocket.sendResponse(response);
                        navigateToScreen("gameScreen.fxml", btnSignOut); // Navigate to the game screen
                    } catch (IOException ex) {
                        Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        textErrorMessage.setText("Error accepting game request: " + ex.getMessage());
                    }
                },
                () -> {

                    // On "No"
                    RefuseStartGameResponse response = new RefuseStartGameResponse(startGameRequest.getUsername());
                    playerSocket.sendResponse(response);

                },
                () -> {
                    
                    // On "Close"
                    RefuseStartGameResponse response = new RefuseStartGameResponse(startGameRequest.getUsername());
                    playerSocket.sendResponse(response);

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