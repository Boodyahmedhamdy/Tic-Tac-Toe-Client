/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.game;

import java.awt.Point;

/**
 *
 * @author HP
 */
public abstract class Game {
    
    public String[][] board = new String[3][3];
    public boolean isDone = false;
    public Player winnerPlayer = null;
    public Point[] winingPoints = new Point[3];
    public Integer status = UNKNOWN;
    
    public static final int UNKNOWN = 0;
    public static final int PLAYER_X_WINS = 1;
    public static final int PLAYER_O_WINS = 2;
    public static final int DRAW = 3;

    protected Player playerX;
    protected Player playerO;
    public Player currentPlayer;
    
    
    /**
     * initializes the game with 2 players and makes the first passed is the 
     * current player so always the player with Symbol 'X'
     * will start. to change this behaveour you can 
     * pass the player with 'O' symbol as the first argument
     * @param playerX
     * @param playerO
    */
    public Game(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = this.playerX;
        initBoard();
    }
    
    /**
     * initialize the board with empty strings to avoid NullPointerExceptions
    */
    protected void initBoard() {
        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                board[i][j] = "";
            }
        }
    }
    
    /**
     * switches the current player to the other player
    */
    public void switchCurrentPlayer() {
        if(currentPlayer.getSymbol() == 'X') currentPlayer = playerO;
        else currentPlayer = playerX;
    }

    /**
     * checks for any situation for a winning through
     * rows, columns and both diagonals
     */
    public void checkBoard() {
        if(!isDone) {
            checkRows();
            checkColumns();
            checkTopLeftToBottomRightDiagonal();
            checkTopRightToBottomLeftDiagonal();
            checkDraw();
        }
    }

    /**
     * iterates over all rows and checks them all
    */
    private void checkRows() {
        for(int row = 0 ; row < 3 ; row++) {
            checkRow(row);
        }
    }

    /**
     * checks if the row with passed index is in winning or not
     * if the row is a winning row -> this method will update the game state
     * and update the winning points and winner player
    */
    private void checkRow(int row) {
        if(
            board[row][0].equals(board[row][1]) &&
            board[row][0].equals(board[row][2]) &&
            !board[row][0].isEmpty() &&
            !board[row][1].isEmpty() &&
            !board[row][2].isEmpty()
        ) {
            System.out.println("Row #" + row + " was checked and it is the winner");
            isDone = true;
            winingPoints[0] = new Point(row, 0);
            winingPoints[1] = new Point(row, 1);
            winingPoints[2] = new Point(row, 2);
            winnerPlayer = getWinnerPlayer();
            updateGameState();
        }
    }

    private void checkColumns() {
        for(int col = 0 ; col < 3 ; col++) {
            checkColumn(col);
        }
    }

    /**
     * checks if the column with passed index is in winning or not
     * if the column is a winning column -> this method will update the game state
     * and update the winning points and winner player
    */
    private void checkColumn(int col) {
        if(
            board[0][col].equals(board[1][col]) &&
            board[0][col].equals(board[2][col]) &&
            !board[0][col].isEmpty() &&
            !board[1][col].isEmpty() &&
            !board[2][col].isEmpty()
        ) {
            System.out.println("Column #" + col + " was checked and it is the winner");
            isDone = true;
            winingPoints[0] = new Point(0, col);
            winingPoints[1] = new Point(1, col);
            winingPoints[2] = new Point(2, col);
            winnerPlayer = getWinnerPlayer();
            updateGameState();
        }
    }

    /**
     * if the diagonal is winning -> this method will update the game state
     * and update the winning points and winner player
    */
    private void checkTopLeftToBottomRightDiagonal() {
        if(
            board[0][0].equals(board[1][1]) &&
            board[0][0].equals(board[2][2]) &&
            !board[0][0].isEmpty() &&
            !board[1][1].isEmpty() &&
            !board[2][2].isEmpty()
        ) {
            isDone = true;
            winingPoints[0] = new Point(0, 0);
            winingPoints[1] = new Point(1, 1);
            winingPoints[2] = new Point(2, 2);
            winnerPlayer = getWinnerPlayer();
            updateGameState();
        }
    }

    /**
     * if the diagonal is winning -> this method will update the game state
     * and update the winning points and winner player
    */
    private void checkTopRightToBottomLeftDiagonal() {
        if(
            board[0][2].equals(board[1][1]) &&
            board[0][2].equals(board[2][0]) &&
            !board[0][2].isEmpty() &&
            !board[1][1].isEmpty() &&
            !board[2][0].isEmpty()
        ) {
            isDone = true;
            winingPoints[0] = new Point(0, 2);
            winingPoints[1] = new Point(1, 1);
            winingPoints[2] = new Point(2, 0);
            winnerPlayer = getWinnerPlayer();
            updateGameState();
        }
    }
    
    /**
     * checks the game for a Draw state where no winner and the board is full
    */
    private void checkDraw() {
        if(isBoardFull() && winnerPlayer == null) {
            status = DRAW;
        }
    }
    
    /**
     * returns whether the board is full or not
    */
    public boolean isBoardFull() {
        for(int i = 0 ; i < 3 ; i++) {
            for (int j = 0 ; j < 3 ; j++) {
                if(board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * called only when the winner is known
    */
    public void updateGameState() {
        if(winnerPlayer.getSymbol() == 'X') status = PLAYER_X_WINS;
        else status = PLAYER_O_WINS;
    }

    /**
     * called when user want to restart the game. it keeps the scores.
    */
    public void restart() {
        isDone = false;
        switchCurrentPlayer();
        status = UNKNOWN;
        winnerPlayer = null; // for empty
        initBoard();
    }
    
    /**
     * happens when user clicks out of game. when the game is done not to play 
     * again. note: it doesn't keep the scores
    */
    public void finish() {
        System.out.println("Game was closed by user");
    }

    /**
     * determines the winner player and returns it 
     * NOTE: this method is used only when there is a winner
    */
    private Player getWinnerPlayer() {
        if(board[winingPoints[0].x][winingPoints[0].y].equals(String.valueOf('X'))) {
            return playerX;
        } else return playerO;
    }
}
