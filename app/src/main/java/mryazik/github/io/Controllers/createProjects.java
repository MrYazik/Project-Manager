package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mryazik.github.io.Classes.FilesWork;
import mryazik.github.io.Classes.modalWindow;

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
                FilesWork.createProject(textFromTextField);
                currentModalStage.close();
            }

        });

        back.setOnAction(event -> {
            currentModalStage.close();
        });
    }
}
