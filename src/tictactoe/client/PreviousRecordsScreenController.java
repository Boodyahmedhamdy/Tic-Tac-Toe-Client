package tictactoe.client;

import tictactoe.client.ui.states.RecordListItem;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * FXML Controller class
 *
 * @author Abdelrahman_Elshreif
 */
public class PreviousRecordsScreenController implements Initializable {

    @FXML
    private Label recordsLabel;
    @FXML
    private Button backBtn;
    @FXML
    private ListView<RecordListItem> recordsListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // We Load The Previous Game From the Text File
        String filePath = "gameRecords.txt";
        List<RecordListItem> recordedGames = GameRecordParser.parseSavedGames(filePath);

        // Adding the Previous Games after parsing to the list view
        recordsListView.getItems().addAll(recordedGames);

        // Customize the display of each item in the ListView
        recordsListView.setCellFactory(param -> new ListCell<RecordListItem>() {
            @Override
            protected void updateItem(RecordListItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // Display only the game name
                    setText(item.getRecordName());
                }
            }
        });

        // Handle selection of a game
        recordsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<String> moves = newSelection.getMoves();
                replayGame(moves);
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
        System.out.println("Game Replay Now ");
    }
}
