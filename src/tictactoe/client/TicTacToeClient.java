
package tictactoe.client;
import java.io.File;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

public class TicTacToeClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneNavigator.setMainStage(primaryStage);
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.show();
        splashScreen.transitionTo(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));
                Scene mainScene = new Scene(root);

                primaryStage.setScene(mainScene);
                primaryStage.setTitle("Tic Tac Toe");
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, javafx.util.Duration.seconds(3));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
