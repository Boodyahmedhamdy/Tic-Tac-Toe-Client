//package tictactoe.client;
//
//import tictactoe.client.ui.states.RecordListItem;
//import java.io.IOException;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.event.ActionEvent;
//import javafx.scene.control.ListView;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListCell;
//import javafx.stage.Stage;
//
///**
// * FXML Controller class
// *
// * @author Abdelrahman_Elshreif
// */
//public class PreviousRecordsScreenController implements Initializable {
//
//    @FXML
//    private Label recordsLabel;
//    @FXML
//    private Button backBtn;
//    @FXML
//    private ListView<RecordListItem> recordsListView;
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//
//        // We Load The Previous Game From the Text File
//        String filePath = "gameRecords.txt";
//        List<RecordListItem> recordedGames = GameRecordParser.parseSavedGames(filePath);
//
//        // Adding the Previous Games after parsing to the list view
//        recordsListView.getItems().addAll(recordedGames);
//
//        // Customize the display of each item in the ListView
//        recordsListView.setCellFactory(param -> new ListCell<RecordListItem>() {
//            @Override
//            protected void updateItem(RecordListItem item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    // Display only the game name
//                    setText(item.getRecordName());
//                }
//            }
//        });
//
//        // Handle selection of a game
//        recordsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                List<String> moves = newSelection.getMoves();
//                replayGame(moves);
//            }
//        });
//    }
//
//    @FXML
//    private void goToPrevScreen(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartOptionsScreen.fxml"));
//            Parent root = loader.load();
//            Stage stage = (Stage) backBtn.getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(PreviousRecordsScreenController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void replayGame(List<String> moves) {
//        try {
//            // Load the GameScreen
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
//            Parent root = loader.load();
//
//            // Get the controller and pass the moves
//            GameScreenController gameScreenController = loader.getController();
//            gameScreenController.setRecordedMoves(moves); // Pass the moves to the GameScreenController
//
//            // Show the GameScreen
//            Stage stage = (Stage) recordsListView.getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(PreviousRecordsScreenController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//}
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

            // Use a single thread to replay all moves
            new Thread(() -> {
                for (int i = 0; i < moves.size(); i++) {
                    String move = moves.get(i);

                    // Skip lines that are not moves (e.g., "Result: Player X Wins")
                    if (!move.matches("[XO]:\\(\\d+, \\d+\\)")) {
                        System.out.println("Skipping non-move line: " + move);
                        continue;
                    }

                    // Parse the move (e.g., "O:(0, 0)")
                    String[] parts = move.split(":");
                    if (parts.length < 2) {
                        System.out.println("Invalid move format: " + move);
                        continue; // Skip invalid moves
                    }

                    char playerSymbol = parts[0].charAt(0); // 'X' or 'O'
                    String[] coordinates = parts[1].replace("(", "").replace(")", "").split(",");
                    if (coordinates.length < 2) {
                        System.out.println("Invalid coordinates: " + move);
                        continue; // Skip invalid coordinates
                    }

                    int row = Integer.parseInt(coordinates[0].trim());
                    int col = Integer.parseInt(coordinates[1].trim());

                    // Debug: Print the parsed move
                    System.out.println("Parsed move: " + playerSymbol + " at (" + row + ", " + col + ")");

                    // Use Platform.runLater to update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        // Update the button text and disable it
                        Button button = gameScreenController.getButtonAtPosition(new Point(row, col));
                        if (button != null) {
                            button.setText(String.valueOf(playerSymbol)); // Set the text (X or O)
                            button.setDisable(true); // Disable the button
                            System.out.println("Updated button at (" + row + ", " + col + ") with " + playerSymbol);
                        } else {
                            System.out.println("Button not found at (" + row + ", " + col + ")");
                        }
                    });

                    // Add a delay between moves (1 second)
                    try {
                        Thread.sleep(1000); // 1-second delay
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
