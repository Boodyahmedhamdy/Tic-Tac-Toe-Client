/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.awt.Point;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import tictactoe.client.game.Game;

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
    private Label player1_score; // x
    @FXML
    private Label player2_score; // o
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
    }    
    
    void updateWinnerScore() {
        switch (game.winnerPlayer) {
            case 'O':
                player1_score.setText(
                        String.valueOf(
                                Integer.parseInt(player1_score.getText())+1
                        )
                );  break;
            case 'X':
                player2_score.setText(
                        String.valueOf(
                                Integer.parseInt(player2_score.getText())+1
                        )
                );  break;
            default:
                // draw
                System.out.println("Draw NO WINNER");
                break;
        }
    }
    
    @FXML
    void handleOnClick(ActionEvent event) {
        if(game.isDone) {
            System.out.println("game is done");
            restartGame();
        } else {
            Button clickedButton = (Button) event.getSource();
        
            Point clickedPosition = getClickedButtonPosition(
                clickedButton
            );

            clickedButton.setText(String.valueOf(game.currentPlayer));
            clickedButton.setDisable(true);

            game.playAt(clickedPosition.x, clickedPosition.y);
            game.switchCurrentPlayer();
            game.checkBoard();
            if(game.isDone) {
                System.out.println("game is done");
                System.out.println("wining points are + " + Arrays.toString(game.winingPoints));
                highlightWiningPoints();
                updateWinnerScore();
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
