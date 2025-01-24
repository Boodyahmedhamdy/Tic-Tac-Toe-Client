
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
        }, javafx.util.Duration.seconds(2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*import javafx.animation.PauseTransition;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TicTacToeClient extends Application {

    @Override
public void start(Stage primaryStage) {
    
    Stage splashStage = new Stage();
    splashStage.initStyle(StageStyle.UNDECORATED);


    ImageView splashImage = new ImageView(new Image("file:C:\\Users\\Laptop World\\Downloads\\Animation - 1736889197440.gif"));
    Label loadingLabel = new Label("Loading...");
    VBox splashLayout = new VBox(10, splashImage, loadingLabel);
    splashLayout.setAlignment(Pos.CENTER);
    

    Scene splashScene = new Scene(splashLayout, 1100, 800);
    splashStage.setScene(splashScene);


    splashStage.show();

    PauseTransition delay = new PauseTransition(Duration.seconds(3));
    delay.setOnFinished(event -> {
        splashStage.close();

          try {
                Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));
                Scene mainScene = new Scene(root);
                primaryStage.setScene(mainScene);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error loading StartOptionsScreen.fxml. Please ensure the file exists in the correct location.");
            }
    });
    delay.play();
}


    public static void main(String[] args) {
        launch(args);
    }
}*/
