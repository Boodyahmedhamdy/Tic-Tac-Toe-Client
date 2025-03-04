package tictactoe.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tictactoe.client.ui.states.RecordListItem;

public class GameRecordParser {

    public static List<RecordListItem> parseSavedGames(String filePath) {
        List<RecordListItem> recordedGames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> moves = new ArrayList<>();
            String gameName = null;
            int gameNumber = 1;

            while ((line = reader.readLine()) != null) {
                // Start of a new game
                if (line.startsWith("----------------------------")) {
                    if (gameName != null) {
                        recordedGames.add(new RecordListItem(gameName, new ArrayList<>(moves)));
                        moves.clear(); // Reset moves for the next game
                    }
                    gameName = "Game (" + gameNumber + ")";
                    gameNumber++;
                } else if (line.startsWith("Result: ")) {
                    moves.add(line);
                } else if (line.matches("[XO]:\\(\\d+, \\d+\\)")) {
                    moves.add(line);
                }
            }

            // Add the last game if it exists
            if (gameName != null && !moves.isEmpty()) {
                recordedGames.add(new RecordListItem(gameName, moves));
            }
        } catch (IOException e) {
            System.err.println("Error reading game records file: " + e.getMessage());
            e.printStackTrace();
        }
        return recordedGames;
    }
}
