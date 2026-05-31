package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mryazik.github.io.App;
import mryazik.github.io.Classes.FilesWork;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.util.elements;
import mryazik.github.io.workData.Projects;
import mryazik.github.io.workData.workJsonFile;

import java.util.ArrayList;
import java.util.List;

public class leftMenu {
    @FXML
    Button create_project;

    public void initialize()
    {
        create_project.setOnAction(event -> {
            modalWindow.StageAndLoader newStageAndLoader = modalWindow.create(App.primaryStage, "create-project.fxml");

            createProjects controller = newStageAndLoader.loader().getController();
            controller.init(newStageAndLoader.stage());
        });
    }

    @FXML
    VBox list_projects;

    public void init()
    {
        // Очищаем лист
        list_projects.getChildren().clear();
        List<Projects> listProject = workJsonFile.getJsonInfo().getProjects();

        for (Projects project : listProject)
        {
            list_projects.getChildren().add(elements.projectInLeftMenu(project.getTitle()));
        }
    }
}
