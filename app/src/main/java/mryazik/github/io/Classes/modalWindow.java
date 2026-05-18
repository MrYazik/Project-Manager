package mryazik.github.io.Classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mryazik.github.io.App;

import java.util.logging.Level;

import static mryazik.github.io.App.logger;

public class modalWindow {
    public static record StageAndLoader(Stage stage, FXMLLoader loader) {}

    public static StageAndLoader create(Stage primaryStage, String fxml_name) // к какому окну будет принадлежать модальное окно
    {
        try {
            Stage newModalWindow = new Stage();
            newModalWindow.initOwner(primaryStage);
            newModalWindow.initModality(Modality.APPLICATION_MODAL);
            newModalWindow.initStyle(StageStyle.TRANSPARENT);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/mryazik/github/io/fxml/" + fxml_name));
            Scene scene = new Scene(loader.load());
            newModalWindow.setScene(scene);
            scene.setFill(Color.TRANSPARENT);

            newModalWindow.show();

            return new StageAndLoader(newModalWindow, loader);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось загрузить модальное окно: " + fxml_name , e);
            return null;
        }
    }
}
