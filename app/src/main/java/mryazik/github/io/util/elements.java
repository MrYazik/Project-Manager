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

import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mryazik.github.io.Classes.layoutLoad;
import mryazik.github.io.Controllers.inProject;

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
        button.setPrefWidth(230.0);
        button.setPrefHeight(23.0);

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
        Label label = new Label("Несохранённые изменения");
        label.setMinWidth(Label.USE_PREF_SIZE); // minWidth="-Infinity"
        label.setTextFill(Color.web("#f59e0c"));
        // System Bold Italic, size 13
        label.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 13.0));

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
}
