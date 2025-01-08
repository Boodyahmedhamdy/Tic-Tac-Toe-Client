package tictactoe.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;

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
    
    @FXML
    TextInputDialog dialog = new TextInputDialog();

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
        
        playOnlineBtn.setOnAction((event) -> {
            System.out.println("يلاا بينا اونلاين ");
        });
        
        prevRecordsBtn.setOnAction((event) -> {
            System.out.println("سيديهاتك كلها هنااا اوعى تنسى نفسك");
        });
        
    }
    
}
