/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class WinVideoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private MediaView win_video; 
    private MediaPlayer mediaPlayer;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         String path = "D:/Rofaida Sobhy/java/TicTacToe/Tic-Tac-Toe-Client/src/tictactoe/client/win-video.mp4";  
          
        //Instantiating Media class  
        Media media = new Media(new File(path).toURI().toString());  
          
        //Instantiating MediaPlayer class   
        mediaPlayer = new MediaPlayer(media);  
           
        win_video.setFitHeight(400); 
        win_video.setFitWidth(500); 
        win_video.setMediaPlayer(mediaPlayer);
        
        //by setting this property to true, the Video will be played   
        //mediaPlayer.setAutoPlay(true);  
        mediaPlayer.play();
        
        
    }   
    
    public MediaPlayer getMediaPlayer(){
    
        return mediaPlayer;
    }
    
}
