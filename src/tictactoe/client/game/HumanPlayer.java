/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.game;

/**
 *
 * @author HP
 */
public class HumanPlayer extends Player{

    
    public HumanPlayer(Character symbol, String username, Integer score) {
        super(symbol, username, score);
    }

    @Override
    public void playAt(String[][] board, Integer x, Integer y) {
        board[x][y] = String.valueOf(this.getSymbol());
    }
    
}
