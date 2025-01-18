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
public abstract class Player {
    
    private Character symbol;
    private String username;
    private Integer score;
    
    
    public abstract void playAt(String[][] board, Integer x, Integer y);

    public Player(String username) {
        this.username = username;
    }

    public Player(Character symbol, String username, Integer score) {
        this.symbol = symbol;
        this.username = username;
        this.score = score;
    }

    public Character getSymbol() {
        return symbol;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
