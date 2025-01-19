/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tictactoe.client.game.Game;
import tictactoe.client.game.HumanPlayer;
import tictactoe.client.game.LocalGameWithFriend;
import tictactoe.client.ui.UiUtils;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class GameScreenController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Label player1_name;
    @FXML
    private Label player1_score; // O
    @FXML
    private Label player2_score; // X

    @FXML
    private Label player2_name;
    @FXML
    private GridPane board;

    Game game;
    Video video;
    int AI_X,AI_Y;
    
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
        
        HumanPlayer playerX = new HumanPlayer('X', "AI", 0);
        HumanPlayer playerO = new HumanPlayer('O', "YOU", 0);
        
        player2_name.setText(playerX.getUsername());
        player1_name.setText(playerO.getUsername());
        player2_score.setText(playerX.getScore().toString());
        player1_score.setText(playerO.getScore().toString());
        game = new LocalGameWithFriend(
                playerX, playerO
        );
        video=new Video();
    }    

    Alert alert;
    
    @FXML
   /* void handleOnClick(ActionEvent event) throws IOException {
        if(game.isDone) {
            System.out.println("game is done");
        } else {
            Button clickedButton = (Button) event.getSource();
        
            Point clickedPosition = getClickedButtonPosition(
                clickedButton
            );
            
            clickedButton.setText(String.valueOf(game.currentPlayer.getSymbol()));
             if ("X".equals(clickedButton.getText())) {
                    clickedButton.getStyleClass().add("x-button");
                } else if ("O".equals(clickedButton.getText())) {
                    clickedButton.getStyleClass().add("o-button");
                }
            clickedButton.setDisable(true);
            
            game.currentPlayer.playAt(game.board, clickedPosition.x, clickedPosition.y);
            game.switchCurrentPlayer();
            game.checkBoard();
            
            switch(game.status) {
                case Game.PLAYER_X_WINS:
                    highlightWiningPoints();
                    game.winnerPlayer.setScore(game.winnerPlayer.getScore()+1);
                    player2_score.setText(String.valueOf(game.winnerPlayer.getScore()));
                    
                    UiUtils.showReplayAlert("Player " + game.winnerPlayer.getUsername() + " Won, Do you want to Replay??",
                            () -> { restartGame(); } ,
                            () -> { 
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
                    game.winnerPlayer.setScore(game.winnerPlayer.getScore() +1);
                    System.out.println("O wins");
            
                    player1_score.setText(String.valueOf(game.winnerPlayer.getScore()));
                    UiUtils.showReplayAlert("Player " + game.winnerPlayer.getUsername() + " Won , Do you want to Replay??",
                            () -> { restartGame(); } ,
                            () -> {
                                /* Go Home Screen*/
                               /* System.out.println("I will Go Home"); 
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
                               /* System.out.println("I will Go Home");
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
    }*/
    
    
   
    void highlightWiningPoints() {
        for(Point point: game.winingPoints) {
            Button button = getButtonAtPosition(point);
            button.setStyle("-fx-background-color:yellow;");
        }
        //vedio win or lose
         
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

   
    public int miniMax(String[][] board, int depth, boolean isMaximizing) {
        game.checkBoard();
        int result = game.status;
        if (result != Game.UNKNOWN || depth == 0) {
            return getScore(result);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = "X";
                        int score = miniMax(board, depth - 1, false);
                        board[i][j] = "";
                        if (score > bestScore) {
                            bestScore = score;
                            AI_X = i;
                            AI_Y = j;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = "O";
                        int score = miniMax(board, depth - 1, true);
                        board[i][j] = "";
                        //bestScore = Math.min(score, bestScore);
                        if (score < bestScore) {
                            bestScore = score;
                            AI_X = i;
                            AI_Y = j;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    public Button getButtonAt(int x, int y) {
        for (Node node : board.getChildren()) {
            Integer col = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);
            col = (col == null) ? 0 : col;
            row = (row == null) ? 0 : row;
            if (col == x && row == y) {
                return (Button) node;
            }
        }
        throw new IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + "). Button not found.");
    }

    private int getScore(int result) {
        switch (result) {
            case Game.PLAYER_X_WINS:
                return 10;
            case Game.PLAYER_O_WINS:
                return -10;
            case Game.DRAW:
                return 0;
            default:
                return 0;
        }
    }

    //AI
    @FXML
    void handleOnClick(ActionEvent event) throws IOException {
        if (game.isDone) {
            System.out.println("game is done");
        } else {
            Button clickedButton = (Button) event.getSource();
            Point clickedPosition = getClickedButtonPosition(clickedButton);

            // Handle human player (O) move
            if (String.valueOf(game.currentPlayer.getSymbol()).equals("O")) {
                clickedButton.setText("O");
                clickedButton.getStyleClass().add("o-button");
                game.currentPlayer.playAt(game.board, clickedPosition.x, clickedPosition.y);
                clickedButton.setDisable(true);

                // Check game status after human move
                game.checkBoard();
                if (game.status != Game.UNKNOWN) {
                    handleGameEnd();
                    return;
                }

                // Switch to AI player
                game.switchCurrentPlayer();

                // AI (X) makes its move
                miniMax(game.board, 10, false);
                Button AIButton = getButtonAt(AI_X, AI_Y);
                AIButton.setText("X");
                AIButton.getStyleClass().add("x-button");
                game.currentPlayer.playAt(game.board, AI_X, AI_Y);
                AIButton.setDisable(true);

                // Check game status after AI move
                game.checkBoard();
                if (game.status != Game.UNKNOWN) {
                    handleGameEnd();
                    return;
                }

                // Switch back to human player
                game.switchCurrentPlayer();
            }
        }
    }

    private void handleGameEnd() {
        switch (game.status) {
            case Game.PLAYER_X_WINS:
                highlightWiningPoints();
                video.loseVideo();
                game.winnerPlayer.setScore(game.winnerPlayer.getScore() + 1);
                player2_score.setText(String.valueOf(game.winnerPlayer.getScore()));
                UiUtils.showReplayAlert("Player " + game.winnerPlayer.getUsername() + " Won, Do you want to Replay?",
                        this::restartGame,
                        () -> navigateToScreenSafely("StartOptionsScreen.fxml"),
                        () -> navigateToScreenSafely("StartOptionsScreen.fxml"));
                break;

            case Game.PLAYER_O_WINS:
                highlightWiningPoints();
                video.winVideo();
                game.winnerPlayer.setScore(game.winnerPlayer.getScore() + 1);
                player1_score.setText(String.valueOf(game.winnerPlayer.getScore()));
                UiUtils.showReplayAlert("Player " + game.winnerPlayer.getUsername() + " Won, Do you want to Replay?",
                        this::restartGame,
                        () -> navigateToScreenSafely("StartOptionsScreen.fxml"),
                        () -> navigateToScreenSafely("StartOptionsScreen.fxml"));
                break;

            case Game.DRAW:
                System.out.println("Draw Happend .. No winner");
                UiUtils.showReplayAlert("DRAW, No Winner, Do you want to Replay?",
                        this::restartGame,
                        () -> navigateToScreenSafely("StartOptionsScreen.fxml"),
                        () -> navigateToScreenSafely("StartOptionsScreen.fxml"));
                break;

            default:
                System.out.println("UNKNOWN ERROR Happend");
                break;
        }
    }

    private void navigateToScreenSafely(String fxmlFile) {
        try {
            navigateToScreen(fxmlFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error happened");
        }
    }


}
