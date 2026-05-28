package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import mryazik.github.io.App;
import mryazik.github.io.Classes.FilesWork;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.util.elements;

import java.util.ArrayList;
import java.util.Map;

public class inProject {
    @FXML
    Label name_project;
    @FXML
    Label readme_output;
    @FXML
    Button change;
    @FXML
    Button add_note;
    @FXML
    VBox list_notes;
    @FXML
    TextArea notes_at_notes;

    public void initialize()
    {
        change.setOnAction(event -> {
            modalWindow.StageAndLoader stageLoader = modalWindow.create(App.primaryStage, "change-readme.fxml");

            changeReadme controller = stageLoader.loader().getController();
            controller.init(name_project.getText(), stageLoader.stage());
        });

        add_note.setOnAction(event -> {
            modalWindow.StageAndLoader loader = modalWindow.create(App.primaryStage, "notes-create-menu.fxml");
            createNotes controller = loader.loader().getController();

            controller.init(loader, name_project.getText());
        });
    }

    public void init(String name_project)
    {
        // Инициализируем называние
        this.name_project.setText(name_project);
        this.readme_output.setText(FilesWork.getReadme(name_project));

        // Подгружаем кнопки
        ArrayList<Map<String, Object>> notes = FilesWork.getProjectIdeas(name_project);

        for (Map<String, Object> map : notes)
        {
            map.forEach((String key, Object object) -> {
                list_notes.getChildren().add(elements.notesButton(key, () -> {
                    notes_at_notes.setText(object.toString());

                    list_notes.getChildren().forEach((Object button) -> {
                        Button toButton = (Button) button;
                        toButton.getStyleClass().removeAll("in_project-inactive-files-menu-button", "in_project-files-menu-button");

                        if(toButton.getText() != key) {
                            toButton.getStyleClass().add("in_project-inactive-files-menu-button");
                        } else {
                            toButton.getStyleClass().add("in_project-files-menu-button");
                        }
                    });
                }));
            });
        }
    }
}
