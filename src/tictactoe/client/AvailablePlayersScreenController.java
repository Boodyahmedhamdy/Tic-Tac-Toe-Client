package tictactoe.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import tictactoe.client.ui.states.UserListItemUiState;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AvailablePlayersScreenController implements Initializable {

    @FXML
    private Text textPlayerUserName;
    @FXML
    private Text textPlayerScore;
    @FXML
    private Button btnSignOut;
    @FXML
    private ListView<UserListItemUiState> lvAvailablePlayers;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvAvailablePlayers.getItems().addAll(
                new UserListItemUiState("boody", 33),
                new UserListItemUiState("Ahmed", 323)
        );

        lvAvailablePlayers
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends UserListItemUiState> observable, UserListItemUiState oldValue, UserListItemUiState newValue) -> {
                    System.out.println(lvAvailablePlayers.getSelectionModel().getSelectedItem());
                });

    }

}
