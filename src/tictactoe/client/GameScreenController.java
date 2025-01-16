/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import tictactoe.client.game.Game;
import tictactoe.client.ui.UiUtils;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class GameScreenController implements Initializable {

    @FXML
    private ColumnConstraints HCenter;
    @FXML
    private Button back;
    @FXML
    private ImageView player1_symbol;
    @FXML
    private Label player1_name;
    @FXML
    private Label player1_score; // O
    @FXML
    private Label player2_score; // X
    @FXML
    private ImageView player2_symbol;
    @FXML
    private Label player2_name;
    @FXML
    private GridPane board;
    @FXML
    private Button btn00;
    @FXML
    private Button btn01;
    @FXML
    private Button btn02;
    @FXML
    private Button btn10;
    @FXML
    private Button btn11;
    @FXML
    private Button btn12;
    @FXML
    private Button btn20;
    @FXML
    private Button btn21;
    @FXML
    private Button btn22;
   
    
    
    Game game;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        back.setOnAction((event) -> {
            try {
                navigateToScreen("StartOptionsScreen.fxml");
            } catch (IOException ex) {
                Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        game = new Game();

    }    

    Alert alert;
    
    @FXML
    void handleOnClick(ActionEvent event) throws IOException {
        //this.event=event;
        if(game.isDone) {
            System.out.println("game is done");
            // restartGame();
        } else {
            Button clickedButton = (Button) event.getSource();
        
            Point clickedPosition = getClickedButtonPosition(
                clickedButton
            );
            
            clickedButton.setText(String.valueOf(game.currentPlayer));
             if ("X".equals(clickedButton.getText())) {
                    clickedButton.getStyleClass().add("x-button");
                } else if ("O".equals(clickedButton.getText())) {
                    clickedButton.getStyleClass().add("o-button");
                }
            clickedButton.setDisable(true);
            
            game.playAt(clickedPosition.x, clickedPosition.y);
            game.switchCurrentPlayer();
            game.checkBoard();
            
            switch(game.status) {
                case Game.PLAYER_X_WINS:
                    // increase x player score
                    highlightWiningPoints();
                    game.playerXScore++;
                    System.out.println("X wins");
                    player2_score.setText(String.valueOf(game.playerXScore));
                    
                    UiUtils.showReplayAlert("Player " + game.winnerPlayer + " Won, Do you want to Replay??",
                            () -> { restartGame(); } ,
                            () -> { 
                                /* Go Home Screen*/ 
                                System.out.println("I will Go Home"); 
                                
                                try {
                                    navigateToScreen("StartOptionsScreen.fxml");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    System.out.println("error happend");
                                }
                                
                            },
                            () -> {
                                System.out.println("Dialog was closed"); 
                                    try {
                                        navigateToScreen("StartOptionsScreen.fxml");
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                        System.out.println("error happend");
                                    }
                            }  );
                    break;
                    
                case Game.PLAYER_O_WINS:
                    // increase y player score
                    highlightWiningPoints();
                    game.playerOScore++;
                    System.out.println("O wins");
                    
                    //openVideoDialog();
                   
                    player1_score.setText(String.valueOf(game.playerOScore));
                    UiUtils.showReplayAlert("Player " + game.winnerPlayer + " Won , Do you want to Replay??",
                            () -> { restartGame(); } ,
                            () -> {
                                /* Go Home Screen*/
                                System.out.println("I will Go Home"); 
                                try {
                                    navigateToScreen("StartOptionsScreen.fxml");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    System.out.println("error happend");
                                }
                            },
                            () -> { 
                                System.out.println("Dialog was closed"); 
                                try {
                                    navigateToScreen("StartOptionsScreen.fxml");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    System.out.println("error happend");
                                }
                            } );
                    break;
                    
                case Game.DRAW:
                    // don't do anything 
                    // open a dialog
                    System.out.println("Draw Happend .. No winner");
                    UiUtils.showReplayAlert("DRAW, No Winner, Do you want to Replay??",
                            () -> { restartGame(); } ,
                            () -> { 
                                /* Go Home Screen*/
                                System.out.println("I will Go Home");
                                try {
                                    navigateToScreen("StartOptionsScreen.fxml");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    System.out.println("error happend");
                                }
                            },
                            () -> {
                                System.out.println("Dialog was closed"); 
                                try {
                                    navigateToScreen("StartOptionsScreen.fxml");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    System.out.println("error happend");
                                }
                            } );
                    break;
                    
                case Game.UNKNOWN:
                    // continue playing
                    System.out.println("game is still on");
                    break;
                    
                default:
                    // show error message
                    System.out.println("UNKNOWN ERROR Happend");
                    break;
            }
        }
    }
    
    void highlightWiningPoints() {
        for(Point point: game.winingPoints) {
            Button button = getButtonAtPosition(point);
            button.setStyle("-fx-background-color:yellow;");
        }
        //vedio win or lose
        openVideoDialog();
    }
    
    Button getButtonAtPosition(Point position) {
        for(Node node : board.getChildren()) {
            Integer x = GridPane.getRowIndex(node);
            if(x == null) x  = 0;
            Integer y = GridPane.getColumnIndex(node);
            if(y == null) y = 0;
            if(x == position.x && y == position.y) {
                return (Button) node;
            }
        }
        return null;
    }
    
    Point getClickedButtonPosition(Button button) {
        Integer x = GridPane.getRowIndex(button);
        Integer y = GridPane.getColumnIndex(button);
        if(x == null) x = 0;
        if (y == null) y = 0;
        return new Point(x, y);
    }
    
    String[][] getBoard() {
        String[][] board = new String[3][3];
        for(int i = 0 ; i < 3 ; i++) {
            for (int j = 0 ; j < 3 ; j++) {
                board[i][j] = getButtonAtPosition(new Point(i, j)).getText();
            }
        }
        
        return board;
    }
    
    void printBoard(String[][] board) {
        for(int i = 0 ; i < 3 ; i++) {
            for (int j = 0 ; j < 3 ; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private void restartGame() {
        game.restart();
        board.getChildren().forEach((node) -> {
            ((Button) node).setText("");
            ((Button) node).setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f2f2f2);");
            ((Button) node).setDisable(false);
        });
    }
    
   private void navigateToScreen(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) board.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
   
  
   
    private void openVideoDialog() {
     
        URL resource = getClass().getResource("/tictactoe/client/win-video.mp4");
        if (resource == null) {
            System.err.println("Video file not found!");
            return;
        }
         Media media = new Media(resource.toString());
       
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
      
       
        mediaView.setFitWidth(500); 
        mediaView.setFitHeight(400); 
        mediaView.setPreserveRatio(true);

    
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Video Dialog");
        
        
        ButtonType closeButton = new ButtonType("Close", ButtonType.CLOSE.getButtonData());
        dialog.getDialogPane().getButtonTypes().add(closeButton);

       
        StackPane mediaPane = new StackPane(mediaView);
        dialog.getDialogPane().setContent(mediaPane);

       
        mediaPlayer.play();
        
        /*dialog.setOnCloseRequest(event -> {
            mediaPlayer.stop();  // Stop the media player
            mediaPlayer.dispose();  // Dispose of the media player resources
        });*/

        // Show the dialog
        dialog.showAndWait();

       
    }

}
