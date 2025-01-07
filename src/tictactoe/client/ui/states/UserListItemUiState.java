/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.ui.states;

/**
 *
 * @author HP
 */
public class UserListItemUiState {
    
    public String username;
    public int score;

    public UserListItemUiState(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public UserListItemUiState(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username + " -> " + score + " points";
    }
    
    
    
    
    
    
    
}
