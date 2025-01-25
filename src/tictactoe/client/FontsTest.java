/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.client;

/**
 *
 * @author Laptop World
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FontsTest extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");

        // الحصول على قائمة بجميع أسماء الخطوط المثبتة
        for (String fontName : Font.getFamilies()) {
            Text text = new Text(fontName);
            text.setFont(new Font(fontName, 16)); // تعيين الخط مع حجمه
            root.getChildren().add(text);
        }

        Scene scene = new Scene(root, 400, 600);
        stage.setTitle("Available Fonts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
