package tictactoe.client;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
            result.ifPresent(ipAddress -> {
                if (!isValidIpAddress(ipAddress)) {
                    iperror.setAlertType(AlertType.ERROR);
                    iperror.setTitle("Connection Failed");
                    iperror.setHeaderText("INVALID IP");
                    iperror.showAndWait();
                }

            });

        });

        prevRecordsBtn.setOnAction((event) -> {
            System.out.println("سيديهاتك كلها هنااا اوعى تنسى نفسك");
        });

    }

    private boolean isValidIpAddress(String ipAddress) {
        // Simple regex for IPv4 address validation
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ipAddress.matches(regex);
    }
}
