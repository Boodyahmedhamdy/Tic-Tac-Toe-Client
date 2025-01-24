
package tictactoe.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {

    private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public static void loadNewScene(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(SceneNavigator.class.getResource(fxmlPath));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
}