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
    public void start(Stage stage) throws Exception {

        //Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));




        Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));

//        Parent root = FXMLLoader.load(getClass().getResource("StartOptionsScreen.fxml"));


//        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));

        // Parent root = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));



       // Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));

       
       /* String path = "D:/Rofaida Sobhy/java/TicTacToe/Tic-Tac-Toe-Client/src/tictactoe/client/win-video.mp4";  
          
        //Instantiating Media class  
        Media media = new Media(new File(path).toURI().toString());  
          
        //Instantiating MediaPlayer class   
        MediaPlayer mediaPlayer = new MediaPlayer(media);  
          
        //Instantiating MediaView class   
        MediaView mediaView = new MediaView(mediaPlayer);  
        
        //by setting this property to true, the Video will be played   
        mediaPlayer.setAutoPlay(true);  
          
        //setting group and scene   
        Group root = new Group();
        root.getChildren().add(mediaView);  
        Scene scene = new Scene(root,500,400);  
        stage.setScene(scene);  
        stage.setTitle("Playing video");  
        stage.show();  */


//         Parent root = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
