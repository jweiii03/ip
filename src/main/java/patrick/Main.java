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
        assert stage != null : "Stage cannot be null";
        assert patrick != null : "PatrickStar instance should be initialized";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "Loaded AnchorPane should not be null";

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Patrick Star");

            MainWindow controller = fxmlLoader.<MainWindow>getController();
            assert controller != null : "Controller should not be null";
            controller.setPatrick(patrick);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
