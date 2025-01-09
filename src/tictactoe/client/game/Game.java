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
    public char currentPlayer = 'X';
    public char winnerPlayer;
    public Point[] winingPoints = new Point[3];

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
        board[x][y] = String.valueOf(currentPlayer);
    }

    public void switchCurrentPlayer() {
        if(currentPlayer == 'X') currentPlayer = 'O';
        else currentPlayer = 'X';
    }

    public void checkBoard() {
        if(!isDone) {
            checkRows();
            checkColumns();
            checkTopLeftToBottomRightDiagonal();
            checkTopRightToBottomLeftDiagonal();
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
        }
    }
    
    public void restart() {
        isDone = false;
        currentPlayer = winnerPlayer;
        winnerPlayer = 'e'; // for empty
        initBoard();
    }
    
}
