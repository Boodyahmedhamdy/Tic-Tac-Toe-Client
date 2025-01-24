package tictactoe.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import network.requests.GetAvailablePlayersRequest;
import network.requests.Request;
import network.requests.StartGameRequest;
import network.responses.AcceptStartGameResponse;
import network.responses.GetAvailablePlayersResponse;
import network.responses.RefuseStartGameResponse;
import network.responses.Response;
import network.responses.StartGameResponse;
import network.responses.SuccessGetAvaialbePlayersResponse;
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
    private ListView<String> lvAvailablePlayers;
    @FXML
    private static Text textErrorMessage;
    
    private static ObservableList<String> availabePlayers;
    
    public static PlayerSocket playerSocket;
    
    PlayerInfo playerInfo;
    
    @FXML
    private Button btnRefresh;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        btnSignOut.setOnAction((event) -> {
//            try {
//                navigateToScreen("StartOptionsScreen.fxml", btnSignOut);
//            } catch (IOException ex) {
//                Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
//                textErrorMessage.setText("Error navigating to StartOptionsScreen: " + ex.getMessage());
//            }
//        });

        playerSocket = PlayerSocket.getInstance();
        playerSocket.startListenerThread();
        playerInfo = PlayerInfo.getInstance();
        textPlayerUserName.setText(playerInfo.getUserName());
        textPlayerScore.setText(playerInfo.getRank() + " points");

//        availabePlayers = FXCollections.observableArrayList();
        availabePlayers = FXCollections.observableArrayList();
        lvAvailablePlayers.setItems(
                availabePlayers
        );
        

        // Add listener for player selection
        lvAvailablePlayers.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
            SignOutAction signOutAction = new SignOutAction(playerInfo.getUserName());
//            playerSocket.sendRequest(signOutAction);
            navigateToScreen("StartOptionsScreen.fxml", btnSignOut);
        } catch (IOException ex) {
            Logger.getLogger(AvailablePlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            textErrorMessage.setText("Error signing out: " + ex.getMessage());
        }
    }

    private void onClickOnPlayer(String username) {
        
        System.out.println("sending Start game request sent to: " + username);
        StartGameRequest startGameRequest = new StartGameRequest(username);
        playerSocket.sendRequest(startGameRequest);
        System.out.println("Start game request sent to: " + username);
    }
    
    @FXML
    private void onRefreshBtnClicked(ActionEvent event) {
        System.out.println("sending GetAvailabePlayers Request...");
        GetAvailablePlayersRequest request = new GetAvailablePlayersRequest();
        playerSocket.sendRequest(request);
        System.out.println("GetAvaialblePlayersRequest sent");
    }
    
    public static void onRecieveGetAvailablePlayersResponse(GetAvailablePlayersResponse response) {
        System.out.println("reciving GetAvaialblePlayersResponse");
        if(response instanceof SuccessGetAvaialbePlayersResponse) {
            SuccessGetAvaialbePlayersResponse successResponse = 
                    (SuccessGetAvaialbePlayersResponse) response;
            System.out.println("before updating list " + availabePlayers);
            System.out.println("incoming list is " + successResponse.getUsernames());
            Platform.runLater(() -> {
                availabePlayers.setAll(successResponse.getUsernames());
                System.out.println("after updating list " + availabePlayers);
            });
            
        } else {
            System.out.println("Failure happend");
        }
    }

    /**
     * Called when a start game request is received from another player via the server.
     */
    public static void onReceiveStartGameRequest(StartGameRequest startGameRequest) {
        Platform.runLater(() -> {
        
         UiUtils.showReplayAlert(
                startGameRequest.getUsername() + " wants to have a game with you. Do you agree?",
                () -> {
                    
                        // On "Yes"
                        AcceptStartGameResponse response = new AcceptStartGameResponse(startGameRequest.getUsername());
                        System.out.println("sending " + response.getClass().getSimpleName() + " to " + response.getUsername());
                        playerSocket.sendResponse(response);
                        System.out.println("Sent " + response.getClass().getSimpleName() + " to " + response.getUsername());

//                      navigateToScreen("gameScreen.fxml", btnSignOut); // Navigate to the game screen
                    
                },
                () -> {

                    // On "No"
                    RefuseStartGameResponse response = new RefuseStartGameResponse(startGameRequest.getUsername());
                    System.out.println("sending " + response.getClass().getSimpleName() + " to " + response.getUsername());
                    playerSocket.sendResponse(response);
                    System.out.println("Sent " + response.getClass().getSimpleName() + " to " + response.getUsername());


                },
                () -> {
                    
                    // On "Close"
                    RefuseStartGameResponse response = new RefuseStartGameResponse(startGameRequest.getUsername());
                    System.out.println("sending " + response.getClass().getSimpleName() + " to " + response.getUsername());
                    playerSocket.sendResponse(response);
                    System.out.println("Sent " + response.getClass().getSimpleName() + " to " + response.getUsername());


                }
            );
        });
        
        
       
    }

    /**
     * Called when a start game response is received from another player via the server.
     */
    public static void onReceiveStartGameResponse(StartGameResponse startGameResponse) {
        if (startGameResponse instanceof AcceptStartGameResponse) {
            // Handle acceptance (e.g., navigate to the game screen)
            
                System.out.println("AcceptStartGameResponse has come and navigating to GameScreen");
//                navigateToScreen("gameScreen.fxml", btnSignOut);
            
        } else if (startGameResponse instanceof RefuseStartGameResponse) {
            // Handle refusal
            Platform.runLater(() -> {
                UiUtils.showValidationAlert("Your invitation to " + startGameResponse.getUsername() + " was refused.");
            });
        }
    }
}