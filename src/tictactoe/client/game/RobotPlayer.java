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
public class RobotPlayer extends Player{
    
    public RobotPlayer(Character symbol, String username, Integer score) {
        super(symbol, username, score);
        
    }

    

    @Override
    public void playAt(String[][] board, Integer x, Integer y) {
        // here you have to call the min max algorithm
        board[x][y] = this.getSymbol().toString();
    }
    
   
    
}
