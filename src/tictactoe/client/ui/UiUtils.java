/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client.ui;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class UiUtils {
    
    
    /***
     * changes the current scene to the path you send to it
     * @param stage
     * @param pathToFxmlFile
     * @throws java.io.IOException
     */
    public void switchTo(Stage stage, URL pathToFxmlFile) throws IOException {
        Parent root  = FXMLLoader.load(pathToFxmlFile);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
