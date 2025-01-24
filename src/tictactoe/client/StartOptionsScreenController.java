
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tictactoe.client.PlayerSocket;
import tictactoe.client.ui.UiUtils;

public class StartOptionsScreenController implements Initializable {

    @FXML
    private Label gameTitle;
    @FXML
    private Button playWithAIbtn;
    @FXML
    private Button playWithFreindBtn;
    @FXML
    private Button playOnlineBtn;
    @FXML
private Button prevRecordsBtn;

    private void setupButtonActions() {
        playWithAIbtn.setOnAction(event -> handlePlayWithAI());
        playWithFreindBtn.setOnAction(event -> handlePlayWithFriend());
        playOnlineBtn.setOnAction(event -> handlePlayOnline());
        prevRecordsBtn.setOnAction(event -> handlePreviousRecords());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupButtonActions();

    }

    @FXML
    private void handlePlayWithAI() {
        System.out.println("Play With AI");
    }
    
    @FXML
    private void handlePlayWithFriend() {
        UiUtils.showReplayAlert("Do you want to record the game ?", () -> {
            GameScreenController.isRecording = true;
            try {
                navigateToScreen("gameScreen.fxml", playWithFreindBtn);
            } catch (IOException ex) {
                Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, () -> {
            GameScreenController.isRecording = false;
            System.out.println("Game Will Not Be Recorded");
            try {
                navigateToScreen("gameScreen.fxml", playWithFreindBtn);
            } catch (IOException ex) {
                Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }, () -> {
            System.out.println("Dialog Closed");
        });

    }
    
    @FXML
    private void handlePlayOnline() {
        Alert ipError = new Alert(AlertType.ERROR);
        TextInputDialog ipPicker = new TextInputDialog("");
        ipPickerDialogCustomize(ipPicker, ipError);

        Optional<String> result = ipPicker.showAndWait();
        result.ifPresent(ip -> {
            if (!isValidIpAddress(ip)) {
                showErrorAlert(ipError, "INVALID IP", "Connection Failed");
            } else {
                try {
                    if (connectToServer(ip)) {
                        navigateToScreen("LoginScreen.fxml", playOnlineBtn);
                    } else {
                        showErrorAlert(ipError, "Cannot Find Server with This IP", "Connection Failed");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @FXML
    private void handlePreviousRecords() {
        try {
            navigateToScreen("PreviousRecordsScreen.fxml", prevRecordsBtn);
        } catch (IOException ex) {
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean connectToServer(String serverIP) {
        System.out.println("Player Initiating Connection");
        Alert connecting = new Alert(AlertType.INFORMATION);
        connecting.getDialogPane().getStylesheets().add(getClass().getResource("ui/styles/Alert_Dialogs_Style.css").toExternalForm());
        connecting.setTitle("Connecting");
        connecting.setHeaderText("Please Wait.....");
        connecting.show();

        try {
            InetSocketAddress serverAddress = new InetSocketAddress(serverIP, 9800);
            System.out.println("Attempting to connect to server at: " + serverAddress);

            PlayerSocket socket = PlayerSocket.getInstance();
            if (!socket.isConnected()) {
                boolean connected = socket.connect(serverAddress, 1000); // Timeout of 1 second
                connecting.close();
                if (connected) {
                    System.out.println("Successfully connected to the server.");
                } else {
                    System.out.println("Failed to connect to the server.");
                }
                return connected;

            } else {
                connecting.close();
                System.out.println("Already connected to the server.");
                return true;
            }

        } catch (Exception e) {
            connecting.close();
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, "Connection failed", e);
            return false;
        }
    }

    public boolean isValidIpAddress(String ipAddress) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ipAddress.matches(regex);
    }

    public void navigateToScreen(String fxmlFile, Button b) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) b.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showErrorAlert(Alert alert, String headerText, String title) {
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private void applyStyles(TextInputDialog ipPicker, Alert alert) {
        ipPicker.getDialogPane().getStylesheets().add(getClass().getResource("ui/styles/Alert_Dialogs_Style.css").toExternalForm());
        alert.getDialogPane().getStylesheets().add(getClass().getResource("ui/styles/Alert_Dialogs_Style.css").toExternalForm());
    }

    private void ipPickerDialogCustomize(TextInputDialog ipPicker, Alert ipError) {
        applyStyles(ipPicker, ipError);
        ipPicker.setHeaderText("Please Enter Server IP");
        ipPicker.setTitle("Connect to Server");
        ipPicker.setContentText("IP:");
        ipPicker.getDialogPane().setPrefSize(400, 250);
        Image image = new Image(getClass().getResourceAsStream("ui/styles/online-game-svgrepo-com.png"));
        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(40);
        imgView.setFitWidth(40);
        ipPicker.setGraphic(imgView);

    }
}