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
public class Game {
    
    public String[][] board = new String[3][3];
    public boolean isDone = false;
    public Character currentPlayer = 'X';
    public Character winnerPlayer = null;
    public Point[] winingPoints = new Point[3];
    public Integer playerXScore = 0;
    public Integer playerOScore = 0;
    public Integer status = UNKNOWN;
    
    /*public static final int UNKNOWN = 0;
    public static final int PLAYER_X_WINS = 1;
    public static final int PLAYER_O_WINS = 2;
    public static final int DRAW = 3;*/
    
    public static final int UNKNOWN = 1;
    public static final int PLAYER_X_WINS = 2;
    public static final int PLAYER_O_WINS = -2;
    public static final int DRAW = 0;
    
    private final String TOP_LEFT = board[0][0];
    private final String TOP_CENTER = board[0][1];
    private final String TOP_RIGHT = board[0][2];
    
    private final String CENTER_LEFT = board[1][0];
    private final String CENTER_CENTER = board[1][1];
    private final String CENTER_RIGHT = board[1][2];

    private final String BOTTOM_LEFT = board[2][0];
    private final String BOTTOM_CENTER = board[2][1];
    private final String BOTTOM_RIGHT = board[2][2];
    
    
    public Game() {
        initBoard();
    }
    
    public Game(char currentPlayer) {
        initBoard();
        this.currentPlayer = currentPlayer;
    }
    
    private void initBoard() {
        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                board[i][j] = "";
            }
        }
    }
    
    
    public void playAt(int x, int y) {
        board[x][y] = currentPlayer.toString();
    }

    public void switchCurrentPlayer() {
        if(currentPlayer == 'X') currentPlayer = 'O';
        else currentPlayer = 'X';
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

    private void checkRows() {
        for(int row = 0 ; row < 3 ; row++) {
            checkRow(row);
        }
    }

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
            winnerPlayer = board[row][0].charAt(0);
            updateGameState();
        }
    }

    private void checkColumns() {
        for(int col = 0 ; col < 3 ; col++) {
            checkColumn(col);
        }
    }

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
            winnerPlayer = board[0][col].charAt(0);
            updateGameState();
        }
    }

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
            winnerPlayer = board[0][0].charAt(0);
            updateGameState();
        }
    }

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
            winnerPlayer = board[0][2].charAt(0);
            updateGameState();
        }
    }
    
    private void checkDraw() {
        if(isBoardFull() && winnerPlayer == null) {
            status = DRAW;
        }
    }
    
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
        if(winnerPlayer == 'X') status = PLAYER_X_WINS;
        else status = PLAYER_O_WINS;
    }

    /**
     * called when user want to restart the game. it keeps the scores.
    */
    public void restart() {
        isDone = false;
//        currentPlayer = winnerPlayer;
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
        
    }
    
}