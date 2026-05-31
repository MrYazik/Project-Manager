package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mryazik.github.io.App;
import mryazik.github.io.Classes.FilesWork;
import mryazik.github.io.Classes.layoutLoad;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.workData.Projects;
import mryazik.github.io.workData.jsonData;
import mryazik.github.io.workData.workJsonFile;

import java.util.ArrayList;
import java.util.List;

public class createProjects {
    @FXML Button done;
    @FXML Button back;
    @FXML TextField input_name_projects;

    Stage currentModalStage;

    public void init(Stage stage)
    {
        currentModalStage = stage;
    }

    public void initialize()
    {
        done.setOnAction(event -> {
            String textFromTextField = input_name_projects.getText();

            if (textFromTextField.equals(""))
            {
                // Добавить ошибку - что файл пустой
            } else {
                jsonData projectInfo = workJsonFile.getJsonInfo();

                // Создаём сам объект проекта
                Projects newProjectObject = new Projects(
                        projectInfo.getCountProjects(),
                        textFromTextField,
                        """
                                    Сюда вы можете добавить информацию о проекте
                                    
                                    Можно воспользоваться данным шаблоном:
                                """
                        );

                List<Projects> listProjects = new ArrayList<>();
                listProjects.add(newProjectObject);

                projectInfo.addProjects(listProjects);
                workJsonFile.changeJson(projectInfo);

                // Подгружаем изменения в интерфейс
                FXMLLoader leftMenuLoader = layoutLoad.loadVBoxInLeft("left-control-list.fxml"); // левое меню
                leftMenu controller = leftMenuLoader.getController();
                controller.init(); // Загружаем список всех проектов

                currentModalStage.close();
            }

        });

        back.setOnAction(event -> {
            currentModalStage.close();
        });
    }
}
