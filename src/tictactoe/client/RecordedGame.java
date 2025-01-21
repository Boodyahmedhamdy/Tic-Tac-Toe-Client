/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import tictactoe.client.game.Game;
import tictactoe.client.game.Player;

/**
 *
 * @author Abdelrahman_Elshreif
 */
public class RecordedGame extends Game {

    private GameRecorder gameRecorder;

    RecordedGame(Player playerX, Player playerO) {
        super(playerX, playerO);
        this.gameRecorder = new GameRecorder();
    }

    public void saveMove(Player player, int x, int y) {
        gameRecorder.recordMove(player.getSymbol(), x, y);
        System.out.println("Move saved: Player " + player.getSymbol() + " at (" + x + ", " + y + ")"); // Debugging
    }

    @Override
    public void checkBoard() {
        super.checkBoard();
        if (super.isDone) {
            System.out.println("Game is done. Saving results..."); // Debugging
            switch (status) {
                case PLAYER_X_WINS:
                    gameRecorder.recordResult("Player X Wins");
                    break;
                case PLAYER_O_WINS:
                    gameRecorder.recordResult("Player O Wins");
                    break;
                case DRAW:
                    gameRecorder.recordResult("Draw");
            }
            System.out.println("Code will save file now");
            String filename = "H://ITI_NATIVE//ITI_JAVA_SE//FINAL_PROJECT//Tic-Tac-Toe-Client/gameRecords.txt";
            gameRecorder.saveToFile(filename);
        }
    }

    @Override
    public void restart() {
        super.restart();
        gameRecorder.clear();
    }
}
