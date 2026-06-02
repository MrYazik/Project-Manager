package mryazik.github.io.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mryazik.github.io.App;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.util.elements;
import mryazik.github.io.workData.Ideas;
import mryazik.github.io.workData.Projects;
import mryazik.github.io.workData.jsonData;
import mryazik.github.io.workData.workJsonFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class inProject {
    public static int current_idea_id = -1;

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
    @FXML
    VBox notes_at_idea_vbox;
    @FXML
    Button add_group;

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

        add_group.setOnAction(event -> {
            modalWindow.StageAndLoader loader = modalWindow.create(App.primaryStage, "create-group.fxml");
            createGroup controller = loader.loader().getController();
            controller.init(loader, name_project.getText());
        });
    }

    AtomicBoolean is_text_apply_on = new AtomicBoolean(false);

    @FXML
    VBox list_groups;

    public void init(String name_project)
    {
        // Инициализируем называние
        this.name_project.setText(name_project);

        // Получаем объект проекта и получаем от туда README.md
        jsonData jsonObject = workJsonFile.getJsonInfo();
        AtomicReference<Projects> current_project = new AtomicReference<>();

        jsonObject.getProjects().forEach((project) -> {
            if (project.getTitle().equals(name_project)) {
                current_project.set(project);
                this.readme_output.setText(project.getReadme());
            }
        });


        // Если сейчас нету активной заметки, то пишем везде то что не одна заметка не выбрана, если есть то подставляем нормальные меню меню
        if (current_idea_id == -1)
        {

        }

        // Получаем список идей
        List<Ideas> ideas = jsonObject.getIdeas();

        ideas.forEach((idea) -> {
            if (current_project.get().isProjectContainsThisNote(idea.getId())) {
                // Добавляем элемент и действие по его нажатию
                list_notes.getChildren().add(elements.notesButton(idea.getTitle(), () -> {
//                    // Очищаем todo меню
//                    list_groups.getChildren().clear();

                    // Если нажать то подгружаются все группы и задачи относящиеся к идее
                    // Группы
                    jsonObject.getGroups().forEach((group) -> {
                        if (group.getIdeaId() == idea.getId())
                        {
                            VBox newGroupElement = elements.createGroupHeader(group.getTitle(), group.getGroupId(), name_project);

                            list_groups.getChildren().add(newGroupElement);

                            // Добавляем задачи
                            jsonObject.getTasks().forEach(task -> {
                                if (task.getGroupsId() == group.getGroupId()) {
                                    newGroupElement.getChildren().add(elements.createTaskRow(task.getNameTask()));
                                }
                            });
                        }
                    });


                    current_idea_id = idea.getId();
                    notes_at_notes.setText(idea.getNote());

                    // Перебираем кнопки и ставим активный класс только у нажатого элемента
                    list_notes.getChildren().forEach((Object button) -> {
                        Button toButton = (Button) button;
                        toButton.getStyleClass().removeAll("in_project-inactive-files-menu-button", "in_project-files-menu-button");

                        if (!toButton.getText().equals(idea.getTitle())) {
                            toButton.getStyleClass().add("in_project-inactive-files-menu-button");
                        } else {
                            HBox apply_menu = elements.createUnsavedChangesMenu();
                            ChangeListener listenner = (observable, oldValue, newValue) -> {
                                if (!oldValue.equals(newValue) && !is_text_apply_on.get()) {
                                    notes_at_idea_vbox.getChildren().add(apply_menu);
                                    is_text_apply_on.set(true);
                                }
                            };

                            // Перед каждым переключением убираем меню подтверждения
                            notes_at_notes.textProperty().removeListener(listenner);
                            notes_at_idea_vbox.getChildren().remove(apply_menu);


                            toButton.getStyleClass().add("in_project-files-menu-button");

                            notes_at_notes.textProperty().addListener(listenner);
                        }
                    });
                }));

                // Если одна из созданных кнопок совпадает по id, то делаем её активной

                if (idea.getId() == current_idea_id)
                {
                    // Перебираем кнопки и ставим активный класс только у нажатого элемента
                    list_notes.getChildren().forEach((Object button) -> {
                        Button toButton = (Button) button;
                        toButton.getStyleClass().removeAll("in_project-inactive-files-menu-button", "in_project-files-menu-button");

                        if (!toButton.getText().equals(idea.getTitle())) {
                            toButton.getStyleClass().add("in_project-inactive-files-menu-button");
                        } else {
                            HBox apply_menu = elements.createUnsavedChangesMenu();
                            ChangeListener listenner = (observable, oldValue, newValue) -> {
                                if (!oldValue.equals(newValue) && !is_text_apply_on.get()) {
                                    notes_at_idea_vbox.getChildren().add(apply_menu);
                                    is_text_apply_on.set(true);
                                }
                            };

                            // Перед каждым переключением убираем меню подтверждения
                            notes_at_notes.textProperty().removeListener(listenner);
                            notes_at_idea_vbox.getChildren().remove(apply_menu);


                            toButton.getStyleClass().add("in_project-files-menu-button");

                            notes_at_notes.textProperty().addListener(listenner);
                        }
                    });
                }
            }
        });
    }
}
