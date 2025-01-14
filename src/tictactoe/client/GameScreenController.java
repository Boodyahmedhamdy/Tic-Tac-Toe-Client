/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
        game = new Game();
        //player1_name=new Label();
        //player2_name=new Label();
   
     
                    
    }    

    Alert alert;
    
    @FXML
    void handleOnClick(ActionEvent event) {
        if(game.isDone) {
            System.out.println("game is done");
            // restartGame();
        } else {
            Button clickedButton = (Button) event.getSource();
            
            Point clickedPosition = getClickedButtonPosition(
                clickedButton
            );
            
            clickedButton.setText(String.valueOf(game.currentPlayer));
            //x >>red & o >>blue
            if ("X".equals(clickedButton.getText())) {  
                clickedButton.getStyleClass().add("x-button");
                //player2_name.setId("player2_name");
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
                    
                    UiUtils.showReplayAlert("Player " + game.winnerPlayer + " Won",
                            () -> { restartGame(); } ,
                            () -> { /* Go Home Screen*/ System.out.println("I will Go Home"); },
                            () -> { System.out.println("Dialog was closed"); }  );
                    break;
                    
                case Game.PLAYER_O_WINS:
                    // increase y player score
                    highlightWiningPoints();
                    game.playerOScore++;
                    System.out.println("O wins");
                    player1_score.setText(String.valueOf(game.playerOScore));
                    UiUtils.showReplayAlert("Player " + game.winnerPlayer + " Won",
                            () -> { restartGame(); } ,
                            () -> { /* Go Home Screen*/ System.out.println("I will Go Home"); },
                            () -> { System.out.println("Dialog was closed"); }  );
                    break;
                    
                case Game.DRAW:
                    // don't do anything 
                    // open a dialog
                    System.out.println("Draw Happend .. No winner");
                    UiUtils.showReplayAlert("DRAW, No Winner",
                            () -> { restartGame(); } ,
                            () -> { /* Go Home Screen*/ System.out.println("I will Go Home"); },
                            () -> { System.out.println("Dialog was closed"); } );
                    break;
                    
                case Game.UNKNOWN:
                    // continue playing
                    System.out.println("game is still on");
                    break;
                    
                default:
                    // show error message
                    break;
            }
        }
    }
    
    void highlightWiningPoints() {
        for(Point point: game.winingPoints) {
            Button button = getButtonAtPosition(point);
            button.setStyle("-fx-background-color:yellow;");
        }
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
    
   
}

