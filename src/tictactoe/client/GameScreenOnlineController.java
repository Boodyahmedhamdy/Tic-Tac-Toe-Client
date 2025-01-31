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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import network.requests.PlayAtRequest;
import network.responses.PlayAtResponse;
import tictactoe.client.ui.UiUtils;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class GameScreenOnlineController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Label PlayerO_name;
    @FXML
    private Label PlayerO_score;
    @FXML
    private Label PlayerX_score;
    @FXML
    private Label PlayerX_name;
    @FXML
    private GridPane gridPane;
    @FXML
    private ColumnConstraints HCenter;
    @FXML
    private Button AI_btn00;
    @FXML
    private Button AI_btn01;
    @FXML
    private Button AI_btn02;
    @FXML
    private Button AI_btn10;
    @FXML
    private Button AI_btn11;
    @FXML
    private Button AI_btn12;
    @FXML
    private Button AI_btn20;
    @FXML
    private Button AI_btn21;
    @FXML
    private Button AI_btn22;

    public static Button[][] board;
    public static boolean isGameOver;
    Button[] winningButtons;
    public static String mySymbol;
    public static String oppositeSymbol;
    public static String CurrentPlayer;
    Video video;
    String winnerSymbol;
    int winnerScore;
    String loserSymbol;
    int loserScore;
    String Result;
    public static String oppositeName;
    public static String myName;
    private PlayerSocket playerSocket;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the game board and player symbols
        playerSocket = PlayerSocket.getInstance();
        playerSocket.setGameScreenOnlineController(this);
        board = new Button[][]{
            {AI_btn00, AI_btn01, AI_btn02},
            {AI_btn10, AI_btn11, AI_btn12},
            {AI_btn20, AI_btn21, AI_btn22}
        };

        isGameOver = false;
        mySymbol = "X";
        oppositeSymbol = "O";
        CurrentPlayer = mySymbol;
        if (mySymbol.equals("X")) {
            PlayerX_name.setText(myName);
            PlayerO_name.setText(oppositeName);
        } else {
            PlayerX_name.setText(oppositeName);
            PlayerO_name.setText(myName);
        }
    }

    @FXML
    private void handleOnClick(ActionEvent event) {
        if (isGameOver) {
            return;
        }
        Button clickedButton = (Button) event.getSource();
        if (!clickedButton.getText().isEmpty()) {
            return;
        }

        Point clickedPosition = getClickedButtonPosition(clickedButton);
        if (CurrentPlayer.equals(mySymbol)) {
            clickedButton.setText(mySymbol);
            clickedButton.setDisable(true);
            clickedButton.setStyle("-fx-font-size: 65px;");
            // Send PlayAt request to the server
            checkWhoIsTheWinner();
            PlayAtRequest playAtRequest = new PlayAtRequest(myName, oppositeName, clickedPosition.x, clickedPosition.y, "O", isGameOver);
            System.out.println("Sending PlayAtRequest To server from username: " + myName);
            playerSocket.sendRequest(playAtRequest);
            System.out.println("after " + myName + "'s move isGameOver= " + isGameOver);
            CurrentPlayer = oppositeSymbol;
        }
    }

    public void checkWhoIsTheWinner() {
        Platform.runLater(() -> {
            if (checkWin(oppositeSymbol)) {
                isGameOver = true;
                highlightLosingPoints();
                updateScoreUI(oppositeSymbol);
                video = new Video();
                video.loseVideo();
                Result = oppositeName + " won, ";
                Replay();
            } else if (checkWin(mySymbol)) {
                isGameOver = true;
                highlightWiningPoints();
                video = new Video();
                video.winVideo();
                updateScoreUI(mySymbol);
                Result = mySymbol + " won, ";
                Replay();
            } else if (isBoardFull()) {
                isGameOver = true;
                updateScoreUI("");
                Replay();

            }
        });
    }

    boolean checkWin(String symbol) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(symbol) && board[i][1].getText().equals(symbol) && board[i][2].getText().equals(symbol)) {
                winningButtons = new Button[]{board[i][0], board[i][1], board[i][2]};
                System.out.println("winner is : row" + i);
                return true;
            }
            if (board[0][i].getText().equals(symbol) && board[1][i].getText().equals(symbol) && board[2][i].getText().equals(symbol)) {
                winningButtons = new Button[]{board[0][i], board[1][i], board[2][i]};
                System.out.println("winner is : column" + i);
                return true;
            }
        }

        if (board[0][0].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][2].getText().equals(symbol)) {
            winningButtons = new Button[]{board[0][0], board[1][1], board[2][2]};
            System.out.println("winner is : diagonal1");
            return true;
        }
        if (board[0][2].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][0].getText().equals(symbol)) {
            winningButtons = new Button[]{board[0][2], board[1][1], board[2][0]};
            System.out.println("winner is : diagonal2");
            return true;
        }
        return false;
    }

    void highlightWiningPoints() {
        for (Button button : winningButtons) {
            button.setStyle("-fx-background-color: yellow;");
        }
    }

    void highlightLosingPoints() {
        for (Button button : winningButtons) {
            button.setStyle("-fx-background-color: red;");
        }
    }

    void updateScoreUI(String playerSymbol) {
        switch (playerSymbol) {
            case "X":
                winnerScore = Integer.parseInt(PlayerX_score.getText()) + 1;
                loserScore = Integer.parseInt(PlayerO_score.getText());
                PlayerX_score.setText(String.valueOf(winnerScore));
                winnerSymbol = "X";
                loserSymbol = "O";
                break;

            case "O":
                winnerScore = Integer.parseInt(PlayerO_score.getText()) + 1;
                loserScore = Integer.parseInt(PlayerX_score.getText());
                PlayerO_score.setText(String.valueOf(winnerScore));
                winnerSymbol = "O";
                loserSymbol = "X";
                break;
            default:
                winnerScore = Integer.parseInt(PlayerO_score.getText());
                loserScore = Integer.parseInt(PlayerX_score.getText());
                winnerSymbol = "";
                loserSymbol = "";
        }
    }

    private void Replay() {
        Platform.runLater(() -> {
            UiUtils.showReplayAlert("Do you want to Replay??",
                    () -> {
                        restartGame();
                    },
                    () -> {
                        try {
                            navigateToScreen("StartOptionsScreen.fxml");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            System.out.println("error happened");
                        }
                    },
                    () -> {
                        System.out.println("Dialog was closed");
                        try {
                            navigateToScreen("StartOptionsScreen.fxml");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            System.out.println("error happened");
                        }
                    });
        });
    }

    private void restartGame() {
        Platform.runLater(() -> {
            isGameOver = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j].setText("");
                    board[i][j].getStyleClass().removeAll("x-button", "o-button");
                    board[i][j].setDisable(false);
                }
            }
            CurrentPlayer = mySymbol;
        });
    }

    private void navigateToScreen(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void OnReceivePlayerAction(PlayAtResponse playAtResponse) {
        System.out.println("Waiting for Player Action...");
        Platform.runLater(() -> {
            board[playAtResponse.getX()][playAtResponse.getY()].setText(playAtResponse.getSymbol());
        });
        isGameOver = playAtResponse.IsGameOver();

        System.out.println("after " + oppositeName + "'s move isGameOver= " + isGameOver);
        CurrentPlayer = mySymbol;

        if (isGameOver) {
            // Check if the current player is the winner
            if (checkWin(mySymbol)) {
                highlightWiningPoints(); // Highlight winner's buttons in yellow
            } else if (checkWin(oppositeSymbol)) {
                highlightLosingPoints(); // Highlight loser's buttons in red
            }
        } else {
            checkWhoIsTheWinner();
        }
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
}
