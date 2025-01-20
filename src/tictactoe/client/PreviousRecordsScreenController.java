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

public class PreviousRecordsScreenController implements Initializable {

    @FXML
    private Label recordsLabel;
    @FXML
    private Button backBtn;
    @FXML
    private ListView<RecordListItem> recordsListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String filePath = "gameRecords.txt";
        List<RecordListItem> recordedGames;
        recordedGames = GameRecordParser.parseSavedGames(filePath);

        if (recordedGames.isEmpty()) {
            recordsLabel.setText("No recorded games found.");
        } else {
            recordsListView.getItems().addAll(recordedGames);
        }

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
            // Load the GameScreen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
            Parent root = loader.load();
            GameScreenController gameScreenController = loader.getController();

            // Show the GameScreen
            Stage stage = (Stage) recordsListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            gameScreenController.disableAllButtons();

            new Thread(() -> {
                for (int i = 0; i < moves.size(); i++) {
                    String move = moves.get(i);

                    if (!move.matches("[XO]:\\(\\d+, \\d+\\)")) {
                        System.out.println("Skipping Winner Line " + move);
                        continue;
                    }

                    String[] parts = move.split(":");
                    char playerSymbol = parts[0].charAt(0); // 'X' or 'O'
                    String[] coordinates = parts[1].replace("(", "").replace(")", "").split(",");
                    if (coordinates.length < 2) {
                        System.out.println("Invalid coordinates: " + move);
                        continue;
                    }

                    int row = Integer.parseInt(coordinates[0].trim());
                    int col = Integer.parseInt(coordinates[1].trim());

                    System.out.println("Parsed move: " + playerSymbol + " at (" + row + ", " + col + ")");

                    Platform.runLater(() -> {
                        // Update the button text and disable it
                        Button button = gameScreenController.getButtonAtPosition(new Point(row, col));

                        if ("X".equals(String.valueOf(playerSymbol))) {
                            button.getStyleClass().add("x-button");
                        } else if ("O".equals(String.valueOf(playerSymbol))) {
                            button.getStyleClass().add("o-button");
                        }
                        button.setText(String.valueOf(playerSymbol));
                        button.setDisable(true);

                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException ex) {
            Logger.getLogger(PreviousRecordsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
