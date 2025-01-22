package tictactoe.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import tictactoe.client.ui.UiUtils;

public class StartOptionsScreenController implements Initializable {

    @FXML
    private Button playWithAIbtn;
    @FXML
    private Button playWithFreindBtn;
    @FXML
    private Button playOnlineBtn;
    @FXML
    private Button prevRecordsBtn;

    private PlayerSocket playerSocket;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerSocket = PlayerSocket.getInstance();
    }

    @FXML
    private void handlePlayWithAI() {
        System.out.println("Play With AI");
    }

    @FXML
    private void handlePlayWithFriend() {
        UiUtils.showReplayAlert("Do you want to record the game?", () -> {
            GameScreenController.isRecording = true;
            try {
                navigateToScreen("gameScreen.fxml", playWithFreindBtn);
            } catch (IOException ex) {
                Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, () -> {
            try {
                GameScreenController.isRecording = false;
                System.out.println("Game will not be recorded.");
                navigateToScreen("gameScreen.fxml", playWithFreindBtn);
            } catch (IOException ex) {
                Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, () -> {
            System.out.println("Dialog closed.");
        });
    }

    @FXML
    private void handlePlayOnline() {
        TextInputDialog ipPicker = new TextInputDialog();
        ipPicker.setTitle("Connect to Server");
        ipPicker.setHeaderText("Enter Server IP Address");
        ipPicker.setContentText("IP Address:");

        Optional<String> result = ipPicker.showAndWait();
        result.ifPresent(ip -> {
            if (!isValidIpAddress(ip)) {
                UiUtils.showValidationAlert("Invalid IP address format!");
                return;
            }

            try {
                InetSocketAddress serverAddress = new InetSocketAddress(ip, 9800); // Default server port
                if (playerSocket.connect(serverAddress, 5000)) { // 5-second timeout
                    System.out.println("Connected to server at: " + ip);
                    navigateToScreen("LoginScreen.fxml", playOnlineBtn);
                } else {
                    UiUtils.showValidationAlert("Failed to connect to the server.");
                }
            } catch (Exception ex) {
                Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
                UiUtils.showValidationAlert("Error connecting to the server: " + ex.getMessage());
            }
        });
    }

    @FXML
    private void handlePreviousRecords() {
        try {
            navigateToScreen("PreviousRecordsScreen.fxml", prevRecordsBtn);
        } catch (IOException ex) {
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            UiUtils.showValidationAlert("Error navigating to previous records screen.");
        }
    }

    private boolean isValidIpAddress(String ipAddress) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ipAddress.matches(regex);
    }

    private void navigateToScreen(String fxmlFile, Button button) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
