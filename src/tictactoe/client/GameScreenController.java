package tictactoe.client;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
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
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
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
    
    public static boolean isRecording;
    
    Alert alert;
    Game game;
    Video video;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
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

        HumanPlayer playerX = new HumanPlayer('X', "Freind", 0);
        HumanPlayer playerO = new HumanPlayer('O', "You", 0);
        
        player2_name.setText(playerX.getUsername());
        player1_name.setText(playerO.getUsername());
        player2_score.setText(playerX.getScore().toString());
        player1_score.setText(playerO.getScore().toString());

        video = new Video();

        if (!isRecording) {
            game = new LocalGameWithFriend(playerX, playerO);
        } else {
            game = new RecordedGame(playerX, playerO);
        }

    }

 
    @FXML
    void handleOnClick(ActionEvent event) throws IOException {
        if (game.isDone) {
            System.out.println("game is done");
        } else {
            Button clickedButton = (Button) event.getSource();

            Point clickedPosition = getClickedButtonPosition(
                    clickedButton
            );
            if (game instanceof RecordedGame) {
                ((RecordedGame) game).saveMove(game.currentPlayer, clickedPosition.x, clickedPosition.y);
            }
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

            switch (game.status) {
                case Game.PLAYER_X_WINS:
                    highlightWiningPoints();
                    game.winnerPlayer.setScore(game.winnerPlayer.getScore() + 1);
                    player2_score.setText(String.valueOf(game.winnerPlayer.getScore()));

                    UiUtils.showReplayAlert("Player " + game.winnerPlayer.getUsername() + " Won, Do you want to Replay??",
                            () -> {
                                restartGame();
                            },
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
                            });
                    break;

                case Game.PLAYER_O_WINS:
                    // increase y player score
                    highlightWiningPoints();
                    game.winnerPlayer.setScore(game.winnerPlayer.getScore() + 1);
                    System.out.println("O wins");

                    player1_score.setText(String.valueOf(game.winnerPlayer.getScore()));
                    UiUtils.showReplayAlert("Player " + game.winnerPlayer.getUsername() + " Won , Do you want to Replay??",
                            () -> {
                                restartGame();
                            },
                            () -> {

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
                            });
                    break;

                case Game.DRAW:
                    // don't do anything 
                    // open a dialog
                    System.out.println("Draw Happend .. No winner");
                    UiUtils.showReplayAlert("DRAW, No Winner, Do you want to Replay??",
                            () -> {
                                restartGame();
                            },
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
                            });
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
        for (Point point : game.winingPoints) {
            Button button = getButtonAtPosition(point);
            button.setStyle("-fx-background-color:yellow;");
        }
        //vedio win or lose
        video.winVideo();

    }

    Button getButtonAtPosition(Point position) {
        for (Node node : board.getChildren()) {
            Integer x = GridPane.getRowIndex(node);
            if (x == null) {
                x = 0;
            }
            Integer y = GridPane.getColumnIndex(node);
            if (y == null) {
                y = 0;
            }
            if (x == position.x && y == position.y) {
                return (Button) node;
            }
        }
        return null;
    }

    Point getClickedButtonPosition(Button button) {
        Integer x = GridPane.getRowIndex(button);
        Integer y = GridPane.getColumnIndex(button);
        if (x == null) {
            x = 0;
        }
        if (y == null) {
            y = 0;
        }
        return new Point(x, y);
    }

    public void disableAllButtons() {
        for (Node node : board.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(true); // Disable the button
            }
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

}

