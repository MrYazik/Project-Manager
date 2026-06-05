package mryazik.github.io.util;


import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontPosture;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mryazik.github.io.App;
import mryazik.github.io.Classes.layoutLoad;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.Controllers.createTask;
import mryazik.github.io.Controllers.inProject;
import mryazik.github.io.workData.Groups;
import mryazik.github.io.workData.jsonData;
import mryazik.github.io.workData.workJsonFile;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class elements {
    static Logger logger = Logger.getLogger(elements.class.getName());

    public static Button projectInLeftMenu(String projectName)
    {
        Button button = new Button(projectName);

        // Основные настройки
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setGraphicTextGap(13.0);
        button.setPrefHeight(37.0);
        button.setPrefWidth(227.0);
        button.getStyleClass().add("left-select-project-button");

        // Графика: круг с тенью
        Circle circle = new Circle(5.0);
        circle.setFill(Color.web("#8b5cf6"));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(0.0);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(16.95);
        dropShadow.setRadius(8.5275);
        dropShadow.setSpread(0.2);
        dropShadow.setWidth(19.16);
        dropShadow.setColor(new Color(0.6139619946479797, 0.4597953259944916, 0.9736841917037964, 1.0));
        circle.setEffect(dropShadow);

        button.setGraphic(circle);

        // Внутренний отступ (padding)
        button.setPadding(new Insets(0, 0, 0, 15.0)); // left=15, остальные 0

        // Внешний отступ снизу (VBox.margin)
        VBox.setMargin(button, new Insets(0, 0, 10, 0));

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = layoutLoad.loadVBoxInCenter("in-project.fxml");
                inProject controller = loader.getController();

                // Сбрасываем текущую идею
                inProject.current_idea_id = -1;
                controller.init(projectName);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Не удалось загрузить in-project", e);
            }
        });

        return button;
    }

    public static Button notesButton(String text, Runnable onClickAction) {
        Button button = new Button();

        // 1. Свойства самой кнопки
        button.setText(text);
        button.setAlignment(Pos.TOP_LEFT);
        button.setGraphicTextGap(8.0);
        button.setMnemonicParsing(false);
        button.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        button.setMinWidth(100.0);
        button.setMinHeight(27.0);
        button.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(button, Priority.ALWAYS);

        // Подключение твоего CSS-класса
        button.getStyleClass().add("in_project-files-menu-button");

        // 2. Настройка <VBox.margin>
        VBox.setMargin(button, new Insets(0, 0, 7.0, 0)); // bottom="7.0"

        // 3. Создание и жесткая вставка одинаковой графики <graphic><ImageView>
        try {
            // Иконка берется из ресурсов твоего проекта
            Image image = new Image(elements.class.getResourceAsStream("/mryazik/github/io/icons/note.png"));
            ImageView imageView = new ImageView(image);

            imageView.setFitHeight(19.0);
            imageView.setFitWidth(12.0);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);

            button.setGraphic(imageView);
        } catch (Exception e) {
            System.err.println("Не удалось загрузить стандартную иконку /icons/note.png");
        }

        // 4. Установка курсора <Cursor fx:constant="HAND" />
        button.setCursor(Cursor.HAND);

        button.setOnAction(event ->  onClickAction.run());

        return button;
    }

    public static HBox createUnsavedChangesMenu() {
        // 1. Создаем и настраиваем корневой HBox
        HBox hbox = new HBox();
        hbox.setId("apply_menu");
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setMinHeight(HBox.USE_PREF_SIZE); // minHeight="-Infinity"
        hbox.setOpacity(0.81);
        hbox.setPrefHeight(42.0);
        hbox.setPrefWidth(352.0);
        hbox.setMinWidth(50);
        hbox.setPadding(new Insets(0, 10.0, 0, 0)); // right="10.0"

        // inline-стили родительского контейнера
        hbox.setStyle(
                "-fx-background-color: #34311f; " +
                        "-fx-border-color: #d97707 transparent transparent transparent; " +
                        "-fx-border-width: 2; " +
                        "-fx-background-radius: 0 0 15 15;"
        );

        // 2. Создаем иконку (ImageView) с эффектом Shadow (Tint)
        ImageView warningIcon = new ImageView();
        warningIcon.setFitHeight(17.0);
        warningIcon.setFitWidth(17.0);
        warningIcon.setPickOnBounds(true);
        warningIcon.setPreserveRatio(true);
        HBox.setMargin(warningIcon, new Insets(0, 10.0, 0, 10.0)); // left="10.0" right="10.0"

        try {
            Image img = new Image(elements.class.getResourceAsStream("/mryazik/github/io/icons/warning.png"));
            warningIcon.setImage(img);
        } catch (Exception e) {
            System.err.println("Не удалось загрузить иконку /icons/warning.png");
        }

        // Настройка эффекта Shadow из FXML для окрашивания иконки
        Shadow shadowEffect = new Shadow();
        shadowEffect.setHeight(0.0);
        shadowEffect.setRadius(0.0);
        shadowEffect.setWidth(0.0);
        shadowEffect.setColor(Color.web("#f59e0c")); // У твоего цвета из FXML веб-аналог #f59e0c
        warningIcon.setEffect(shadowEffect);

        // 3. Создаем текстовую метку (Label)
        Label label = new Label("Несохранённые изменения внутри заметки к идее");
        label.setMinWidth(Label.USE_PREF_SIZE); // minWidth="-Infinity"
        label.setTextFill(Color.web("#f59e0c"));
        // System Bold Italic, size 13
        label.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 12.0));

        // 4. Создаем невидимую распорку (Pane-пружину)
        Pane spacer = new Pane();
        spacer.setMaxHeight(Double.MAX_VALUE); // Double.MAX_VALUE заменяет 1.7976931348623157E308
        spacer.setMaxWidth(Double.MAX_VALUE);
        spacer.setMinWidth(10.0);
        spacer.setPrefHeight(200.0);
        spacer.setPrefWidth(200.0);
        // Заставляем HBox растягивать этот Pane, толкая кнопки вправо
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // 5. Создаем кнопку "Откатить"
        Button rollbackButton = new Button("Откатить");
        rollbackButton.setMinHeight(Button.USE_PREF_SIZE);
        rollbackButton.setMinWidth(Button.USE_PREF_SIZE);
        rollbackButton.setMnemonicParsing(false);
        rollbackButton.setOpacity(0.77);
        rollbackButton.setPrefHeight(28.0);
        rollbackButton.setPrefWidth(71.0);
        rollbackButton.setTextFill(Color.web("#f43f5e"));
        rollbackButton.setCursor(Cursor.HAND);
        rollbackButton.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 12.0));
        HBox.setMargin(rollbackButton, new Insets(0, 0, 0, 5.0)); // left="5.0"
        rollbackButton.setStyle(
                "-fx-border-radius: 7; " +
                        "-fx-background-radius: 7; " +
                        "-fx-border-color: #4d2d37; " +
                        "-fx-background-color: #21262d; " +
                        "-fx-border-width: 2;"
        );

        // 6. Создаем кнопку "Изменить"
        Button changeButton = new Button("Изменить");
        changeButton.setMinHeight(Button.USE_PREF_SIZE);
        changeButton.setMinWidth(Button.USE_PREF_SIZE);
        changeButton.setMnemonicParsing(false);
        changeButton.setTextFill(Color.WHITE);
        changeButton.setCursor(Cursor.HAND);
        changeButton.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 12.0));
        HBox.setMargin(changeButton, new Insets(0, 0, 0, 10.0)); // left="10.0"

        // Подключаем стиль и класс из FXML
        changeButton.getStyleClass().add("done_button");
        changeButton.setStyle("-fx-border-radius: 10;");

        // 7. Собираем всё дерево элементов вместе внутрь HBox
        hbox.getChildren().addAll(warningIcon, label, spacer, rollbackButton, changeButton);

        return hbox;
    }

    /**
     * ФУНКЦИЯ 1: Создает независимую шапку группы.
     * @param groupName Имя группы (автоматически переводится в UPPERCASE)
     */
    public static VBox createGroupHeader(String groupName, int group_id, String name_project) {
        VBox all = new VBox();

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPrefHeight(32.0);
        header.setPrefWidth(597.0);

        // Название группы
        Label groupLabel = new Label(groupName.toUpperCase());
        groupLabel.setMinWidth(HBox.USE_PREF_SIZE);
        groupLabel.setTextFill(Color.web("#8b949e"));
        groupLabel.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 12.0));

        // Пружина-распорка для выталкивания меню вправо
        Pane spacer = new Pane();
        spacer.setPrefHeight(200.0);
        spacer.setPrefWidth(100000.0);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Кнопка-меню группы (три горизонтальные точки)
        MenuButton groupMenuButton = new MenuButton("MenuButton");
        groupMenuButton.setAlignment(Pos.CENTER);
        groupMenuButton.setGraphicTextGap(0.0);
        groupMenuButton.setMinHeight(HBox.USE_PREF_SIZE);
        groupMenuButton.setMinWidth(HBox.USE_PREF_SIZE);
        groupMenuButton.setMnemonicParsing(false);
        groupMenuButton.setPrefHeight(15.0);
        groupMenuButton.setPrefWidth(31.0);
        groupMenuButton.setCursor(Cursor.HAND);
        groupMenuButton.getStyleClass().add("three-dots-button");
        groupMenuButton.setStyle("-fx-background-color: transparent;");

        ImageView icon = createShadowIcon("/mryazik/github/io/icons/three_point_horizontal.png", 16.0, 16.0, Color.web("#8b949e"), 0.0);
        groupMenuButton.setGraphic(icon);

        // Пункты меню группы
        MenuItem rename = new MenuItem("Переименовать группу", createShadowIcon("/mryazik/github/io/icons/edit.png", 16.0, 16.0, Color.web("#484f58"), 1.0));
        MenuItem createTask = new MenuItem("Создать задачу", createShadowIcon("/mryazik/github/io/icons/add.png", 16.0, 16.0, Color.web("#484f58"), 1.0));
        MenuItem completeAll = new MenuItem("Завершить всё", createShadowIcon("/mryazik/github/io/icons/array-left-up.png", 16.0, 16.0, Color.web("#484f58"), 1.0));
        MenuItem delete = new MenuItem("Удалить раздел", createShadowIcon("/mryazik/github/io/icons/bin.png", 16.0, 16.0, Color.RED, 1.0));
        delete.setStyle("-fx-text-fill: red;");

        // Создаём задачу
        createTask.setOnAction(event -> {
            modalWindow.StageAndLoader loader = modalWindow.create(App.primaryStage, "create-task.fxml");
            createTask controller = loader.loader().getController();
            controller.init(loader, group_id, name_project);
        });

        // Удаление группы
        delete.setOnAction(event -> {
            jsonData jsonObject = workJsonFile.getJsonInfo();

            jsonObject.getGroups().forEach(group -> {
                if (group.getGroupId() == group_id) {
                    jsonObject.deleteGroup(group);
                    workJsonFile.changeJson(jsonObject);

                    FXMLLoader loader = layoutLoad.loadVBoxInCenter("in-project.fxml");
                    inProject controller = loader.getController();
                    controller.init(name_project);
                }
            });
        });

        groupMenuButton.getItems().addAll(rename, createTask, completeAll, delete);
        header.getChildren().addAll(groupLabel, spacer, groupMenuButton);

        all.getChildren().add(header);
        return all;
    }

    /**
     * ФУНКЦИЯ 2: Создает независимую строку задачи.
     * @param taskText Текст задачи
     */
    public static HBox createTaskRow(String taskText, int id, String name_project) {
        HBox taskRow = new HBox();
        taskRow.setAlignment(Pos.CENTER_LEFT);
        taskRow.setPrefHeight(44.0);
        taskRow.setPrefWidth(597.0);
        taskRow.getStyleClass().add("task");
        taskRow.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

        // Чекбокс задачи
        CheckBox checkBox = new CheckBox(taskText);
        checkBox.setGraphicTextGap(7.0);
        checkBox.setMaxWidth(Double.MAX_VALUE);
        checkBox.setMinWidth(HBox.USE_PREF_SIZE);
        checkBox.setMnemonicParsing(false);
        checkBox.setTextFill(Color.WHITE);
        checkBox.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 13.0));
        HBox.setHgrow(checkBox, Priority.ALWAYS);

        // Пружина-распорка
        Pane spacer = new Pane();
        spacer.setPrefHeight(200.0);
        spacer.setPrefWidth(1000.0);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Кнопка-меню задачи (три вертикальные точки)
        MenuButton taskMenuButton = new MenuButton();
        taskMenuButton.setAlignment(Pos.CENTER);
        taskMenuButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        taskMenuButton.setGraphicTextGap(0.0);
        taskMenuButton.setMaxHeight(HBox.USE_PREF_SIZE);
        taskMenuButton.setMaxWidth(HBox.USE_PREF_SIZE);
        taskMenuButton.setMinHeight(16.0);
        taskMenuButton.setMinWidth(16.0);
        taskMenuButton.setMnemonicParsing(false);
        taskMenuButton.setPrefHeight(121.0);
        taskMenuButton.setPrefWidth(20.0);
        taskMenuButton.setCursor(Cursor.HAND);
        taskMenuButton.getStyleClass().add("three-dots-button");
        taskMenuButton.setStyle("-fx-background-color: transparent;");

        ImageView icon = createShadowIcon("/mryazik/github/io/icons/three_point_vertical.png", 16.0, 16.0, Color.web("#8b949e"), 1.0);
        taskMenuButton.setGraphic(icon);

        // Пункты меню задачи
        MenuItem rename = new MenuItem("Переименовать задачу", createShadowIcon("/mryazik/github/io/icons/edit.png", 16.0, 16.0, Color.web("#282a2e"), 1.0));
        MenuItem details = new MenuItem("Детали задачи", createShadowIcon("/mryazik/github/io/icons/info.png", 16.0, 16.0, Color.web("#282a2e"), 1.0));
        MenuItem changeGroup = new MenuItem("Сменить группу", createShadowIcon("/mryazik/github/io/icons/group.png", 16.0, 16.0, Color.web("#282a2e"), 1.0));
        MenuItem delete = new MenuItem("Удалить задачу", createShadowIcon("/mryazik/github/io/icons/bin.png", 16.0, 16.0, Color.RED, 1.0));
        delete.setStyle("-fx-text-fill: red;");

        // Действие на кнопку удаление задачи
        delete.setOnAction(action -> {
            jsonData jsonObject = workJsonFile.getJsonInfo();

            // Получаем задачу
            jsonObject.getTasks().forEach(task -> {
                if (task.getTaskId() == id)
                {
                    jsonObject.deleteTask(task);
                    workJsonFile.changeJson(jsonObject);

                    FXMLLoader loader = layoutLoad.loadVBoxInCenter("in-project.fxml");
                    inProject controller = loader.getController();
                    controller.init(name_project);
                }
            });
        });

        taskMenuButton.getItems().addAll(rename, details, changeGroup, delete);
        taskRow.getChildren().addAll(checkBox, spacer, taskMenuButton);

        VBox.setMargin(taskRow, new Insets(0.0, 0.0, 7.0, 0.0));

        logger.log(Level.INFO, "Загружена новая задача: " + taskText);
        return taskRow;
    }

    // Вспомогательный метод генерации иконок с эффектом Shadow (один в один как в FXML)
    private static ImageView createShadowIcon(String path, double w, double h, Color color, double shadowHeight) {
        ImageView iv = new ImageView();
        iv.setFitWidth(w);
        iv.setFitHeight(h);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        try {
            iv.setImage(new Image(elements.class.getResourceAsStream(path)));
        } catch (Exception e) {
            System.err.println("Иконка не найдена: " + path);
        }
        Shadow shadow = new Shadow(0.0, color);
        shadow.setHeight(shadowHeight);
        iv.setEffect(shadow);
        return iv;
    }
}
