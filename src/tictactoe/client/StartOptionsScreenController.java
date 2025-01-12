package tictactoe.client;

import java.io.IOException;
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
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.DialogPane;

/**
 * FXML Controller class
 *
 * @author Abdelrahman_Elshreif
 */
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        playWithAIbtn.setOnAction((event) -> {
            System.out.println("هخش دلوقتى احط على البووت");
        });

        playWithFreindBtn.setOnAction((event) -> {
            System.out.println("هسحققققك");
        });

        playOnlineBtn.setOnAction((outerevent) -> {
//            String ip;
            Alert iperror = new Alert(AlertType.ERROR);
            TextInputDialog ipPicker = new TextInputDialog("");

            ipPicker.getDialogPane().getStylesheets().add(getClass().getResource("ui/styles/Alert_Dialogs_Style.css").toExternalForm());
            iperror.getDialogPane().getStylesheets().add(getClass().getResource("ui/styles/Alert_Dialogs_Style.css").toExternalForm());

            ipPicker.setHeaderText("Please Enter Server IP");
            ipPicker.setTitle("Connect to Server");
            ipPicker.setContentText("IP:");
            ipPicker.getDialogPane().setPrefSize(400, 250);

            Image image = new Image(getClass().getResourceAsStream("ui/styles/online-game-svgrepo-com.png"));
            ImageView imgView = new ImageView(image);
            imgView.setFitHeight(40);
            imgView.setFitWidth(40);
            ipPicker.setGraphic(imgView);

            Optional<String> result = ipPicker.showAndWait();
            result.ifPresent(ip -> {
                if (!isValidIpAddress(ip)) {
                    iperror.setAlertType(AlertType.ERROR);
                    iperror.setTitle("Connection Failed");
                    iperror.setHeaderText("INVALID IP");
                    iperror.showAndWait();
                } else {
                    try {
                        if (connectToServer(ip)) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
                            Parent root = loader.load();
                            Stage stage = (Stage) playOnlineBtn.getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } else {
                            iperror.setAlertType(AlertType.ERROR);
                            iperror.setTitle("Connection Failed");
                            iperror.setHeaderText("Can not Find Server with This IP");
                            iperror.showAndWait();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });

        });

        prevRecordsBtn.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PreviousRecordsChooseOptionsScreen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) prevRecordsBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PreviousRecordsChooseOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private boolean isValidIpAddress(String ipAddress) {
        // Simple regex for IPv4 address validation
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ipAddress.matches(regex);
    }

    private boolean connectToServer(String serverIP) {
        try {
            System.out.println("Player Initiating Connection");
            Alert connecting = new Alert(AlertType.INFORMATION);
            connecting.getDialogPane().getStylesheets().add(getClass().getResource("ui/styles/Alert_Dialogs_Style.css").toExternalForm());
            connecting.setTitle("Connecting");
            connecting.setHeaderText("Please Wait.....");
            connecting.showAndWait();
            Socket soc = new Socket(serverIP, 9800);
            connecting.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(StartOptionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
}
