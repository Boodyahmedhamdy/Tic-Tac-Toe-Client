package tictactoe.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.client.ui.states.RecordListItem;

/**
 *
 * @author Abdelrahman_Elshreif
 */
public class GameRecordParser {

    public static List<RecordListItem> parseSavedGames(String filePath) {
        List<RecordListItem> recordedGames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder gameData = new StringBuilder();
            String gameName = "Game (1)";
            int gameNumber = 1;
            List<String> moves = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("----------------------------")) {
                    RecordListItem game = new RecordListItem(gameName, new ArrayList<>(moves));
                    recordedGames.add(game);
                    gameData = new StringBuilder();
                    moves.clear(); // Reset moves for the next game
                    gameNumber++;
                    gameName = "Game(" + gameNumber + ")";
                } else {
                    gameData.append(line).append("\n");
                    if (line.startsWith("x:") || line.startsWith("O:")) {
                        moves.add(line);
                    }
                }
            }

            // Parse the last game
            if (gameData.length() > 0) {
                RecordListItem game = new RecordListItem(gameName, moves);
                recordedGames.add(game);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordedGames;
    }
}
