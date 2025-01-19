/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.game;

import java.util.ArrayList;

/**
 *
 * @author Abdelrahman_Elshreif
 */
public class RecordedGame {

    ArrayList<XYPoint> playersActions = new ArrayList<>(9);

    public void addPlayerAction(XYPoint point) {
        playersActions.add(point);
    }
    public void display(){
        System.out.println("");
    }
}
