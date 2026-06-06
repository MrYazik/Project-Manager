package mryazik.github.io.Controllers;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class inProject {
    public static int current_idea_id = -1;
    public static boolean isNoteCanChangedManually = false;
    Logger logger = Logger.getLogger(inProject.class.getName());

    @FXML
    VBox in_project;


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

    @FXML
    VBox list_groups;

    @FXML VBox null_note; // Поверх заметки к идее
    @FXML VBox null_task; // Поверх списка задач

    public void init(String name_project)
    {
        list_notes.getChildren().clear();

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

        // Получаем список идей
        List<Ideas> ideas = jsonObject.getIdeas();
        AtomicBoolean unsavedMenuHas = new AtomicBoolean(false); // если имеется меню несохранённых изменений


        // Ставим отслеживание изменения заметки к идее
        notes_at_notes.textProperty().addListener((obs, oldText, newText) -> {
            HBox unsavedMenu =
                    elements.createUnsavedChangesMenu(current_idea_id, newText);

            // Если заметку можно изменять вручную
            if (isNoteCanChangedManually) {
                if (unsavedMenuHas.get())
                {
                    in_project.getChildren().removeLast();
                }

                in_project.getChildren().add(unsavedMenu);
                unsavedMenuHas.set(true);
            } else {
                in_project.getChildren().remove(unsavedMenu);
                unsavedMenuHas.set(false);
                logger.log(Level.INFO, "Было переключение, пользователь не изменил текст заметки вручную");
            }
        });

        ideas.forEach((idea) -> {
            if (current_project.get().isProjectContainsThisNote(idea.getId())) {

                // Добавляем элемент и действие по его нажатию
                list_notes.getChildren().add(elements.notesButton(idea.getTitle(), () -> {
                    if (unsavedMenuHas.get())
                    {
                        in_project.getChildren().removeLast();
                        unsavedMenuHas.set(false);
                    }

                    isNoteCanChangedManually = false; // ставим то что заметку нельзя изменять вручную

                    current_idea_id = idea.getId();
                    notes_at_notes.setText(idea.getNote());

                    isNoteCanChangedManually = true;

                    // Очищаем todo меню
                    list_groups.getChildren().clear();

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
                                    newGroupElement.getChildren().add(elements.createTaskRow(task.getNameTask(), task.getTaskId(), name_project));
                                }
                            });
                        }
                    });

                    // Убираем заглушку что нет заметки
                    null_note.setVisible(false);
                    // Убираем заглушку что нет списка задач
                    null_task.setVisible(false);

                    // Перебираем кнопки и ставим активный класс только у нажатого элемента
                    list_notes.getChildren().forEach((Object button) -> {
                        Button toButton = (Button) button;
                        toButton.getStyleClass().removeAll("in_project-inactive-files-menu-button", "in_project-files-menu-button");

                        if (!toButton.getText().equals(idea.getTitle())) { // если не активная кнопка
                            toButton.getStyleClass().add("in_project-inactive-files-menu-button");

                        } else {
                            toButton.getStyleClass().add("in_project-files-menu-button"); // Добавляем активный дизайн
                        }
                    });
                }));
            }
        });


        // Если сейчас нету активной заметки, то пишем везде то что не одна заметка не выбрана, если есть то подставляем нормальные меню меню
        if (current_idea_id == -1)
        {
            null_note.setVisible(true);
            null_task.setVisible(true);
        } else {
            AtomicReference<Ideas> current_idea = new AtomicReference<>(new Ideas());

            jsonObject.getIdeas().forEach(idea -> {
                if (idea.getId() == current_idea_id) {
                    current_idea.set(idea);
                }
            });

            // Перебираем кнопки и ставим активный класс только у нажатого элемента
            list_notes.getChildren().forEach((Object button) -> {
                Button toButton = (Button) button;
                toButton.getStyleClass().removeAll("in_project-inactive-files-menu-button", "in_project-files-menu-button");

                if (!toButton.getText().equals(current_idea.get().getTitle())) { // если не активная кнопка
                    toButton.getStyleClass().add("in_project-inactive-files-menu-button");
                    System.out.println("Кнопка: " + ((Button) button).getText() + " не активная");
                } else {
                    toButton.getStyleClass().add("in_project-files-menu-button");
                    System.out.println("Кнопка: " + ((Button) button).getText() + " активная");// Добавляем активный дизайн
                }
            });


            notes_at_notes.setText(current_idea.get().getNote());



            // Очищаем todo меню
            list_groups.getChildren().clear();

            // Если нажать то подгружаются все группы и задачи относящиеся к идее
            // Группы
            jsonObject.getGroups().forEach((group) -> {
                if (group.getIdeaId() == current_idea.get().getId())
                {
                    VBox newGroupElement = elements.createGroupHeader(group.getTitle(), group.getGroupId(), name_project);
                    list_groups.getChildren().add(newGroupElement);

                    // Добавляем задачи
                    jsonObject.getTasks().forEach(task -> {
                        if (task.getGroupsId() == group.getGroupId()) {
                            newGroupElement.getChildren().add(elements.createTaskRow(task.getNameTask(), task.getTaskId(), name_project));
                        }
                    });
                }
            });

            null_note.setVisible(false);
            null_task.setVisible(false);

        }
    }
}
