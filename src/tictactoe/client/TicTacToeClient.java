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


        //Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));

//        Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));



   //     Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));


//        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
         Parent root = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));



       // Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
