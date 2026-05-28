package mryazik.github.io.util;


import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;

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
}
