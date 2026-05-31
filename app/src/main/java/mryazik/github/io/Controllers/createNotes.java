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
import mryazik.github.io.workData.Ideas;
import mryazik.github.io.workData.jsonData;
import mryazik.github.io.workData.workJsonFile;

import java.util.ArrayList;
import java.util.List;

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
            // Создаём объект json и создаём там идею, потом перезаписываем
            jsonData jsonObject = workJsonFile.getJsonInfo();

            int id = jsonObject.getCountIdeas();
            Ideas newIdea = new Ideas(id, title_idea.getText(), text_idea.getText(), 1);
            List<Ideas> listIdeas = new ArrayList<>();
            listIdeas.add(newIdea);

            // Записываем идею в объект
            jsonObject.addIdeas(listIdeas);

            // Относим идею к проекту
            jsonObject.getProjects().forEach(project -> {
                if (project.getTitle().equals(name_project))
                {
                    List<Integer> idIdeas = new ArrayList<>();
                    idIdeas.add(id);

                    project.addIdeas(idIdeas);
                }
            });

            // Перезаписываем json
            workJsonFile.changeJson(jsonObject);

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
