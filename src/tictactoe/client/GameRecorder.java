package tictactoe.client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelrahman_Elshreif
 */
public class GameRecorder {

    private List<String> moves;
    private String result;

    public GameRecorder() {
        moves = new ArrayList<>();
        result = "";
    }

    public void recordMove(Character player, int x, int y) {
        String move = String.format("Player %s placed at (%d, %d)", player, x, y);
        moves.add(move);

    }

    public void recordResult(String result) {
        this.result = result;
    }

    public void saveToFile(String filename) {
        System.out.println("Saving game history to: " + filename); 
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename, true);
            for (String move : moves) {
                writer.write(move + "\n");
            }
            writer.write("Result: " + result + "\n"); 
            writer.write("----------------------------\n");
            System.out.println("Game history saved successfully."); 
        } catch (IOException ex) {
            System.err.println("Error saving game to file: " + ex.getMessage());
            ex.printStackTrace(); 
        } finally {
            if (writer != null) {
                try {
                    writer.close(); 
                } catch (IOException ex) {
                    System.err.println("Error closing FileWriter: " + ex.getMessage());
                }
            }
        }
    }

    public void clear() {
        moves.clear();
        result = "";
    }

}
