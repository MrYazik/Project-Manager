package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mryazik.github.io.Classes.FilesWork;
import mryazik.github.io.Classes.layoutLoad;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.util.normalizeFileName;

public class createNotes {
    modalWindow.StageAndLoader stageAndLoader;
    String name_project;

    @FXML
    TextArea text_idea;
    @FXML
    TextField title_idea;

    @FXML
    Button close;
    @FXML
    Button done;

    /*
    * Добавить фильтрацию имени файла, чтоб во всех файловых системах работало нормально
    * Добавть дешифровку именни файла
    *
    * Всё вынести в утилиту
    * */

    public void initialize()
    {
        close.setOnAction(event -> {
            stageAndLoader.stage().close();
        });

        done.setOnAction(event -> {
            FilesWork.createIdea(name_project, normalizeFileName.normFile(title_idea.getText()), normalizeFileName.normFile(text_idea.getText()));

            FXMLLoader loader = layoutLoad.loadVBoxInCenter("in-project.fxml");
            inProject controller = loader.getController();
            controller.init(name_project); // Обновляем список всех заметок

            stageAndLoader.stage().close();
        });
    }

    public void init(modalWindow.StageAndLoader stageAndLoader, String name_project)
    {
        this.name_project = name_project;
        this.stageAndLoader = stageAndLoader;
    }
}
