package tictactoe.client;

import java.awt.Point;
import tictactoe.client.ui.states.RecordListItem;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

public class GameScreenRecordController implements Initializable {

    private static final String GAME_RECORDS_FILE = "gameRecords.txt";
    private static final int REPLAY_DELAY_MS = 1000; // 1 second delay between moves

    @FXML
    private Label recordsLabel;
    @FXML
    private Button backBtn;
    @FXML
    private ListView<RecordListItem> recordsListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadRecordedGames();
        setupListView();
    }

    private void loadRecordedGames() {
        List<RecordListItem> recordedGames = GameRecordParser.parseSavedGames(GAME_RECORDS_FILE);
        if (recordedGames.isEmpty()) {
            recordsLabel.setText("No recorded games found.");
        } else {
            recordsListView.getItems().addAll(recordedGames);
        }
    }

    private void setupListView() {
        recordsListView.setCellFactory(param -> new ListCell<RecordListItem>() {
            @Override
            protected void updateItem(RecordListItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getRecordName());
            }
        });

        recordsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                replayGame(newSelection.getMoves());
            }
        });
    }

    @FXML
    private void goToPrevScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartOptionsScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PreviousRecordsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void replayGame(List<String> moves) {
        if (moves == null || moves.isEmpty()) {
            System.out.println("No moves to replay.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
            Parent root = loader.load();
            GameScreenController gameScreenController = loader.getController();

            Stage stage = (Stage) recordsListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            Task<Void> replayTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (String move : moves) {
                        if (isCancelled()) {
                            break;
                        }

                        if (!isValidMove(move)) {
                            System.out.println("Skipping invalid move: " + move);
                            continue;
                        }

                        Point position = parseMove(move);
                        char playerSymbol = move.charAt(0);

                        Platform.runLater(() -> {
                            Button button = gameScreenController.getButtonAtPosition(position);
                            if (button != null) {
                                button.setText(String.valueOf(playerSymbol));
                                button.setDisable(true);
                            }
                        });

                        Thread.sleep(REPLAY_DELAY_MS);
                    }
                    return null;
                }
            };

            new Thread(replayTask).start();
        } catch (IOException ex) {
            Logger.getLogger(PreviousRecordsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isValidMove(String move) {
        return move.matches("[XO]:\\(\\d+, \\d+\\)");
    }

    private Point parseMove(String move) {
        String[] parts = move.split(":");
        String[] coordinates = parts[1].replace("(", "").replace(")", "").split(",");
        int row = Integer.parseInt(coordinates[0].trim());
        int col = Integer.parseInt(coordinates[1].trim());
        return new Point(row, col);
    }
}