/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.game;

import java.awt.Point;

/**
 *
 * @author Ahmed
 */
public class MiniMax {

    public static Point findBestMove(String[][] board) {
        int bestVal = Integer.MIN_VALUE;
        Point bestMove = new Point(-1, -1);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    board[i][j] = "O";
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = "";

                    if (moveVal > bestVal) {
                        bestMove.x = i;
                        bestMove.y = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimax(String[][] board, int depth, boolean isMax) {
        int score = evaluate(board);

        if (score == 10) return score - depth;
        if (score == -10) return score + depth;
        if (isBoardFull(board)) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = "O";
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = "";
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = "X";
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = "";
                    }
                }
            }
            return best;
        }
    }

    private static int evaluate(String[][] board) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][1].equals(board[row][2])) {
                if (board[row][0].equals("O")) return 10;
                else if (board[row][0].equals("X")) return -10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(board[1][col]) && board[1][col].equals(board[2][col])) {
                if (board[0][col].equals("O")) return 10;
                else if (board[0][col].equals("X")) return -10;
            }
        }

        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            if (board[0][0].equals("O")) return 10;
            else if (board[0][0].equals("X")) return -10;
        }

        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            if (board[0][2].equals("O")) return 10;
            else if (board[0][2].equals("X")) return -10;
        }

        return 0;
    }

    private static boolean isBoardFull(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}

