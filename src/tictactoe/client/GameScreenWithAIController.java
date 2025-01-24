/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import tictactoe.client.ui.UiUtils;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class GameScreenWithAIController implements Initializable {

    @FXML
    private Button back;
    //GameWithAI gameWithAi;
   
    
   
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
    Button [][] board;
    boolean isGameOver;
    Button[] winningButtons;
    public static String difficulty;
    String CurrentPlayer;
    String Player;
    String AI;
    Video video;
    @FXML
    private Label You_score;
    @FXML
    private Label AI_score;
    String Result;
    @FXML
    private Label Player_human;
    @FXML
    private Label player_AI;
    
    
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
     
        Player="O";
        AI="X";
        CurrentPlayer=Player;
        //Player_human.setStyle("-fx-background-color:#D3D3D3;");
        //player_AI.setStyle("-fx-background-color:none;");
        //////////////
        board=new Button[][]{
            {AI_btn00,AI_btn01,AI_btn02},
            {AI_btn10,AI_btn11,AI_btn12},
            {AI_btn20,AI_btn21,AI_btn22}
        };
        isGameOver=false;
        //setDifficulty(difficulty);
        System.out.println("Difficulty set to: " + difficulty);
        

        
        
    }    
    
    
 
    
    @FXML
    void handleOnClick(ActionEvent event) throws IOException {
        
            if (isGameOver)
                return;
            Button clickedButton = (Button) event.getSource();
            if(!clickedButton.getText().isEmpty()) 
                return;
            if (CurrentPlayer.equals(Player)) {
                clickedButton.setText(Player);
                //clickedButton.getStyleClass().add("o-button");

                // Switch to AI player
                CurrentPlayer=AI;
                //player_AI.setStyle("-fx-background-color:#D3D3D3;");
                //Player_human.setStyle("-fx-background-color:none;");
               
                checkWhoIsTheWinner();
                
                if(!isGameOver){
                    computerMove();
                    
                }
                   
            
            }
        clickedButton.setDisable(true);

    }
   
    boolean checkWin(String symbol){
    
         // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(symbol) && board[i][1].getText().equals(symbol) && board[i][2].getText().equals(symbol)) {
                winningButtons=new Button[]{board[i][0],board[i][1],board[i][2]};
                return true;
            }
            if(board[0][i].getText().equals(symbol) && board[1][i].getText().equals(symbol) && board[2][i].getText().equals(symbol)) {
                winningButtons=new Button[]{board[0][i],board[1][i],board[2][i]};
                return true;
            }
        }
        
        if (board[0][0].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][2].getText().equals(symbol)){
            winningButtons=new Button[]{board[0][0],board[1][1],board[2][2]};
            return true;
        }
        if(board[0][2].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][0].getText().equals(symbol)) {
            winningButtons=new Button[]{board[0][2],board[1][1],board[2][0]};
            return true;
        }
        return false;
    }
    void checkWhoIsTheWinner(){
        if(checkWin(AI)){
            isGameOver=true;
            highlightWiningPoints();
            video=new Video();
            video.loseVideo();
            AI_score.setText(String.valueOf(Integer.parseInt(AI_score.getText())+1));
            Result="AI won, ";
            Replay();
        }
        else if(checkWin(Player)){
            isGameOver=true;
            highlightWiningPoints();
            video=new Video();
            video.winVideo();
            You_score.setText(String.valueOf(Integer.parseInt(You_score.getText())+1));
            Result="You won, ";
            Replay();
        }
        else if(isBoardFull()){
            isGameOver=true;
            Result="Tie, ";
            Replay();
        }
        
        //Player_human.setStyle("-fx-background-color:none;");
        //player_AI.setStyle("-fx-background-color:none;");
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
    
    void highlightWiningPoints() {
        for(Button button :winningButtons) {
            button.setStyle("-fx-background-color:yellow;");
        }
          
    }
    void computerMove(){
        int[] bestMove=null;
        switch(difficulty){
            case "Easy":
                bestMove=getRandomMove();
                break;
            case "Medium":
                bestMove=findBestMove(3);
                break;
            case "Hard":
                bestMove=findBestMove(Integer.MAX_VALUE);
                break;
        }
        
        if(bestMove!=null){
            int row=bestMove[0];
            int col=bestMove[1];
            board[row][col].setText(AI);
            /*Button clickedButton =getButtonAt(row,col);
            clickedButton.getStyleClass().add("x-button");*/
            CurrentPlayer=Player;
            //Player_human.setStyle("-fx-background-color:#D3D3D3;");
            //player_AI.setStyle("-fx-background-color:none;");
            checkWhoIsTheWinner();
        }
    }
    int[] getRandomMove(){
        List<int[]> emptyCells=new ArrayList<>();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j].getText().isEmpty()){
                    emptyCells.add(new int[]{i,j});
                }
            }
        }
        
        if(!emptyCells.isEmpty()){
            
            return emptyCells.get(new Random().nextInt(emptyCells.size()));
        }
        return null;
    }
    int[] findBestMove(int depthLimit){
        int bestScore = Integer.MIN_VALUE; 
        int[] bestMove = null; 
        

        // Traverse all cells, evaluate minimax function  
        // for all empty cells. And return the cell  
        // with optimal value. 
        for (int i = 0; i < 3; i++) 
        { 
            for (int j = 0; j < 3; j++) 
            { 
                // Check if cell is empty 
                if (board[i][j].getText().isEmpty()) 
                { 
                    // Make the move 
                    board[i][j].setText(AI); 

                    // compute evaluation function for this 
                    // move. 
                    int score = minimax(false, 0, depthLimit); 

                    // Undo the move 
                    board[i][j].setText(""); 

                    // If the value of the current move is 
                    // more than the best value, then update 
                    // best/ 
                    if (score > bestScore) 
                    {  
                        bestScore = score;
                        bestMove=new int[]{i,j};
                    } 
                } 
            } 
        } 

        return bestMove; 
    }
   
    int minimax(boolean isMax,int depth,int depthLimit ) 
    { 
        
        if (checkWin(AI)) 
            return 10-depth; 

       
        if (checkWin(Player)) 
            return depth-10; 

        
        if (isBoardFull()|| depth>=depthLimit) 
            return 0; 

        // If this maximizer's move 
        if (isMax) 
        { 
            int bestScore = Integer.MIN_VALUE; 

            // Traverse all cells 
            for (int i = 0; i < 3; i++) 
            { 
                for (int j = 0; j < 3; j++) 
                { 
                    // Check if cell is empty 
                    if (board[i][j].getText().isEmpty()) 
                    { 
                        // Make the move 
                        board[i][j].setText(AI); 

                        // Call minimax recursively and choose 
                        // the maximum value 
                        int score =  minimax(false,depth + 1, depthLimit); 
                        // Undo the move 
                        board[i][j].setText(""); 
                        bestScore=Math.max(bestScore,score);
                    } 
                } 
            } 
            return bestScore; 
        } 

        // If this minimizer's move 
        else
        { 
            int bestScore = Integer.MAX_VALUE; 

            // Traverse all cells 
            for (int i = 0; i < 3; i++) 
            { 
                for (int j = 0; j < 3; j++) 
                { 
                    // Check if cell is empty 
                   if (board[i][j].getText().isEmpty())  
                    { 
                        // Make the move 
                        board[i][j].setText(Player); 

                        // Call minimax recursively and choose 
                        // the minimum value 
                        int score =  minimax(true,depth + 1, depthLimit); 

                        // Undo the move 
                       board[i][j].setText(""); 
                       bestScore=Math.min(bestScore,score); 
                    } 
                } 
            } 
            return bestScore; 
        } 
    } 
    
    
    
   
    
     
     
    private void navigateToScreen(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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
    
    private void Replay(){
        
        UiUtils.showReplayAlert( Result + "Do you want to Replay??",
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
    
    }

   
    private void restartGame() {
        isGameOver=false;
        gridPane.getChildren().forEach((node) -> {
            ((Button) node).setText("");
            ((Button) node).setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f2f2f2);");
            ((Button) node).setDisable(false);
        });
        //CurrentPlayer=Player;
    }
    
    
    
}
