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
public class LocalGameWithFriend extends Game{

    public LocalGameWithFriend(Player playerX, Player playerO) {
        super(playerX, playerO);
    }

    @Override
    public void finish() {
        System.out.println("Game with Friend was closed by player");
    }
}
