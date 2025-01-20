/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.game;

/**
 *
 * @author LENOVO
 */
public class GameWithAI {
    
    public static int AI_X;
    public static int AI_Y;
    public static final int UNKNOWN=1;
    public static final int AI_WIN=10;
    public static final int PLAYER_WIN=-10;
    public static final int DRAW=0;
    
    public int checkGameStatus(String[][] board) {
        if (hasWon(board, "X")) {
            return 10; // AI wins
        }
        if (hasWon(board, "O")) {
            return -10; // Player O wins
        }
        if (isBoardFull(board)) {
            return 0; // Draw
        }
        return UNKNOWN; // The game is still ongoing
    }
    
    private boolean hasWon(String[][] board, String player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if ((board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) || 
                (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player))) {
                return true;
            }
        }
        if ((board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
            (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player))) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int miniMax(String[][] board, int depth, boolean isMaximizing) {
       
        int result = checkGameStatus(board);
        if (result != 1 || depth == 0) {
            return result;
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
    
    
    
}
