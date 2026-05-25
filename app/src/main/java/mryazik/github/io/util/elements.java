package mryazik.github.io.util;


import com.sun.javafx.logging.PlatformLogger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;

import javafx.geometry.Insets;
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
}
