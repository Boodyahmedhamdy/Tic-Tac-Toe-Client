/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ahmed
 */
public class Video {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void winVideo(ActionEvent event) throws IOException{
       root=FXMLLoader.load(getClass().getResource("WinVideo.fxml"));
       stage=(Stage)((Node)event.getSource()).getScene().getWindow();
       scene=new Scene(root);
       stage.setScene(scene);
       stage.show();
    }
    public void loseVideo(ActionEvent event) throws IOException{
       root=FXMLLoader.load(getClass().getResource("WinVideo.fxml"));
       stage=(Stage)((Node)event.getSource()).getScene().getWindow();
       scene=new Scene(root);
       stage.setScene(scene);
       stage.show();
    }
    
}
