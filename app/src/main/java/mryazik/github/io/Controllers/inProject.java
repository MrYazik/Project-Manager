package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import mryazik.github.io.App;
import mryazik.github.io.Classes.FilesWork;
import mryazik.github.io.Classes.modalWindow;

public class inProject {
    @FXML
    Label name_project;

    @FXML
    Label readme_output;

    @FXML
    Button change;

    public void initialize()
    {
        change.setOnAction(event -> {
            modalWindow.StageAndLoader stageLoader = modalWindow.create(App.primaryStage, "change-readme.fxml");

            changeReadme controller = stageLoader.loader().getController();
            controller.init(name_project.getText(), stageLoader.stage());
        });
    }

    public void init(String name_project)
    {
        // Инициализируем называние
        this.name_project.setText(name_project);
        this.readme_output.setText(FilesWork.getReadme(name_project));
    }
}
