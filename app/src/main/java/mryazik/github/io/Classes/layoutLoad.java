package mryazik.github.io.Classes;

import javafx.fxml.FXMLLoader;
import mryazik.github.io.App;

import java.util.logging.Level;

public class layoutLoad {

    public static void load(String name_file)
    {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/"));
        } catch (Exception e) {
            App.logger.log(Level.WARNING, "Не удалось загрузить слой: " + name_file);
        }
    }
}
