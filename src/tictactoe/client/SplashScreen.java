/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Laptop World
 */
public class SplashScreen {
    private final Stage splashStage;

    public SplashScreen() {
        splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED); 

        ImageView splashImage = new ImageView(new Image("file:C:\\Users\\Laptop World\\Downloads\\Animation - 1736889197440.gif"));
        splashImage.setFitWidth(300); 
        splashImage.setPreserveRatio(true); 

        Label loadingLabel = new Label("Loading...");
        loadingLabel.setStyle("-fx-font-size: 60px; -fx-text-fill:#FFFFFF; -fx-font-family: \"Arabic Typesetting\"; "); 
        VBox splashLayout = new VBox(20, splashImage, loadingLabel);
        splashLayout.setAlignment(Pos.CENTER);
       splashLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #D87DFD, #13C1D7);");
        Scene splashScene = new Scene(splashLayout, 1100, 800);
        splashStage.setScene(splashScene);
    }

    public void show() {
        splashStage.show();
    }

    public void close() {
        splashStage.close();
    }

    public void transitionTo(Runnable onFinish, Duration delay) {
        PauseTransition pause = new PauseTransition(delay);
        pause.setOnFinished(event -> {
            close(); 
            if (onFinish != null) {
                onFinish.run();
            }
        });
        pause.play(); 
    }
    
}
