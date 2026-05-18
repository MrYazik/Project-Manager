package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import mryazik.github.io.App;
import mryazik.github.io.Classes.modalWindow;

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
}
