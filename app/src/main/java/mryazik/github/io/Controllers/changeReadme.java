package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mryazik.github.io.Classes.FilesWork;
import mryazik.github.io.Classes.layoutLoad;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.workData.jsonData;
import mryazik.github.io.workData.workJsonFile;

public class changeReadme {
    Stage stage;
    String name_project;

    @FXML
    Button done;
    @FXML
    Button back;
    @FXML
    TextArea edit;

    public void initialize()
    {
        done.setOnAction(event -> {
            jsonData jsonObject = workJsonFile.getJsonInfo();

            jsonObject.getProjects().forEach((project) -> {
                if (project.getTitle().equals(name_project))
                {
                    project.changeReadme(edit.getText());

                    // Перезаписываем файл
                    workJsonFile.changeJson(jsonObject);
                }
            });


            // Обновляем, чтоб изменения применились
            FXMLLoader loader = layoutLoad.loadVBoxInCenter("in-project.fxml");
            inProject controller = loader.getController();
            controller.init(name_project);

            stage.close();
        });

        back.setOnAction(event -> {
            stage.close();
        });
    }

    public void init(String name_project, Stage stage)
    {
        this.stage = stage;
        this.name_project = name_project;

        // Получаем объект и получаем от туда readme
        jsonData jsonObject = workJsonFile.getJsonInfo();

        jsonObject.getProjects().forEach((project) -> {
            if (project.getTitle().equals(name_project))
            {
                edit.setText(project.getReadme());
            }
        });

    }
}
