package tictactoe.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author HP
 */
public class TicTacToeClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {

<<<<<<< HEAD
        //Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));
=======
//        Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));
>>>>>>> faa2dd41b86b64566a515c18db1c735b68d04f1c


   //     Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));


//        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
         Parent root = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));


        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
