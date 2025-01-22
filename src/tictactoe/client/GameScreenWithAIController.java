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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tictactoe.client.game.GameWithAI;
import tictactoe.client.game.Game_AI;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class GameScreenWithAIController implements Initializable {

    @FXML
    private Button back;
    GameWithAI gameWithAi;
   
    String CurrentPlayer;
    public static String [][] board;
    int status;
     @FXML
    private GridPane gridPane;
    @FXML
    private Label player1_score;
    @FXML
    private Label player2_score;
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
    @FXML
    private Label Player_human;
    @FXML
    private Label player_AI;
    String Player;
    String AI;
    //Game_AI game_AI;
    Game_AI.Move bestMove;
    
    /**
     * Initializes the controller class.
     */
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        back.setOnAction((event) -> {
            try {
                navigateToScreen("StartOptionsScreen.fxml");
            } catch (IOException ex) {
                Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       // game_AI=new Game_AI();
        gameWithAi=new GameWithAI();
        Player="O";
        AI="X";
        CurrentPlayer=Player;
        board=new String[3][3];
        initBoard(board);
    }    
    
    
     //AI
   /* @FXML
    void handleOnClick(ActionEvent event) throws IOException {
        
            Button clickedButton = (Button) event.getSource();
            Point clickedPosition = getClickedButtonPosition(clickedButton);

            // Handle human player (O) move
            if (CurrentPlayer.equals("O")) {
                clickedButton.setText("O");
                clickedButton.getStyleClass().add("o-button");
                board[clickedPosition.x][clickedPosition.y]="O";
                clickedButton.setDisable(true);

                // Check game status after human move
                status= gameWithAi.checkGameStatus(board);
                if (status != gameWithAi.UNKNOWN) {
                    //handleGameEnd();
                    return;
                }

                // Switch to AI player
                CurrentPlayer=AI;

                // AI (X) makes its move
                gameWithAi.miniMax(board, 9, false);
                Button AIButton = getButtonAt(GameWithAI.AI_X,GameWithAI.AI_Y);
                AIButton.setText("X");
                AIButton.getStyleClass().add("x-button");
                board[GameWithAI.AI_X][GameWithAI.AI_X]="X";
                AIButton.setDisable(true);

                // Check game status after AI move
                status= gameWithAi.checkGameStatus(board);
                if (status != gameWithAi.UNKNOWN) {
                    //handleGameEnd();
                    return;
                }

                // Switch back to human player
                CurrentPlayer=Player;
            
        }
    }*/

    
    @FXML
    void handleOnClick(ActionEvent event) throws IOException {
        
            Button clickedButton = (Button) event.getSource();
            Point clickedPosition = getClickedButtonPosition(clickedButton);

            // Handle human player (O) move
            if (CurrentPlayer.equals("O")) {
                clickedButton.setText("O");
                clickedButton.getStyleClass().add("o-button");
                board[clickedPosition.x][clickedPosition.y]="O";
                clickedButton.setDisable(true);

                // Check game status after human move
                status= gameWithAi.checkGameStatus(board);
                if (status != gameWithAi.UNKNOWN) {
                    //handleGameEnd();
                    return;
                }

                // Switch to AI player
                CurrentPlayer=AI;
                
               
                // AI (X) makes its move
                bestMove = Game_AI.findBestMove(board); 
                Button AIButton = getButtonAt(bestMove.row,bestMove.col);
                AIButton.setText("X");
                AIButton.getStyleClass().add("x-button");
                board[bestMove.row][bestMove.col]="X";
                AIButton.setDisable(true);

                // Check game status after AI move
                status= gameWithAi.checkGameStatus(board);
                if (status != gameWithAi.UNKNOWN) {
                    //handleGameEnd();
                    return;
                }

                // Switch back to human player
                CurrentPlayer=Player;
            
        }
    }
    /*private void handleGameEnd() {
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
    }*/
    Point getClickedButtonPosition(Button button) {
        Integer x = GridPane.getRowIndex(button);
        Integer y = GridPane.getColumnIndex(button);
        if(x == null) x = 0;
        if (y == null) y = 0;
        return new Point(x, y);
    }
    
    void initBoard(String[][] board){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j]="";
            }
        }
    }
    
     public Button getButtonAt(int x, int y) {
        for (Node node : gridPane.getChildren()) {
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
     
    private void navigateToScreen(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
