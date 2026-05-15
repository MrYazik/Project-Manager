package mryazik.github.io.Classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import mryazik.github.io.App;

import java.util.logging.Level;

public class layoutLoad {

    public static FXMLLoader loadVBoxInCenter(String name_vbox_file)
    {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/" + name_vbox_file));
            VBox rootLayout = loader.load();

            App.rootLayout.setCenter(rootLayout);
            return loader; // для получения контроллера
        } catch (Exception e) {
            App.logger.log(Level.WARNING, "Не удалось загрузить слой: " + name_vbox_file);
            return null;
        }
    }
    public static FXMLLoader loadVBoxInLeft(String name_vbox_file)
    {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/" + name_vbox_file));
            VBox rootLayout = loader.load();

            App.rootLayout.setLeft(rootLayout);
            return loader; // для получения контроллера
        } catch (Exception e) {
            App.logger.log(Level.WARNING, "Не удалось загрузить слой: " + name_vbox_file);
            return null;
        }
    }


}
