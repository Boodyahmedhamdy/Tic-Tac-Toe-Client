/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;
import java.net.URL;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.WindowEvent;


/**
 *
 * @author Ahmed
 */
public class Video {
    
    private final String WINPATH;
    private final String LOSEPATH;
    private final int MEDIA_VIEW_WEDTH;
    private final int MEDIA_VIEW_HEIGHT;
    private final String WINNER_TITLE;
    private final String LOSER_TITLE;
    private URL resource;
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Dialog<Void> dialog;
    private StackPane mediaPane ;

    public Video() {
        this.WINPATH = "/tictactoe/client/resources/win-video.mp4";
        this.LOSEPATH="/tictactoe/client/resources/lose-video.mp4";
        resource=null;
        this.MEDIA_VIEW_WEDTH=500;
        this.MEDIA_VIEW_HEIGHT=400;
        dialog = new Dialog<>();
        this.WINNER_TITLE="Congratulations!";
        this.LOSER_TITLE="Hard luck!";
    }
    public void winVideo() {
     
        setResource(WINPATH);
        makeVideo(WINNER_TITLE);
       
    }
    
    public void loseVideo(){
        setResource(LOSEPATH);
        makeVideo(LOSER_TITLE);
    }

    public void makeVideo(String videoTitle){
        media = new Media(resource.toString());
       
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
      
       
        mediaView.setFitWidth(MEDIA_VIEW_WEDTH); 
        mediaView.setFitHeight(MEDIA_VIEW_HEIGHT); 
        mediaView.setPreserveRatio(true);

    
        
        dialog.setTitle(videoTitle);
 
        mediaPane = new StackPane(mediaView);
        dialog.getDialogPane().setContent(mediaPane);

       
        mediaPlayer.play();
        
       
        dialog.setOnShown(event -> {
            dialog.getDialogPane().getScene().getWindow().setOnCloseRequest((WindowEvent e) -> {
                mediaPlayer.stop();
            });
        });
        
        dialog.showAndWait();

    }
    public void setResource(String videoPath){
        resource = getClass().getResource(videoPath);
        if (resource == null) {
            System.err.println("Video file not found!");
            return;
        }
    }

    
}
