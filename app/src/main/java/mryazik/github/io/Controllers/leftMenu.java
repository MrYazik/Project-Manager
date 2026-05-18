package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import mryazik.github.io.App;
import mryazik.github.io.Classes.modalWindow;

public class leftMenu {
    @FXML
    Button create_project;

    public void initialize()
    {
        create_project.setOnAction(event -> {
            modalWindow.create(App.primaryStage, "create-project.fxml");
        });
    }
}
