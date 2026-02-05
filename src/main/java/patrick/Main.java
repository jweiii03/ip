package patrick;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for PatrickStar chatbot using FXML.
 */
public class Main extends Application {
    private PatrickStar patrick = new PatrickStar("./data/patrick.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Patrick Star");
            fxmlLoader.<MainWindow>getController().setPatrick(patrick);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
