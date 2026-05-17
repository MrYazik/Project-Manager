package mryazik.github.io.util;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class createElements {
    public Button createProjectInListButton(String projectName) {
        Button button = new Button(projectName);
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setGraphicTextGap(13.0);
        button.setPrefHeight(37.0);
        button.setPrefWidth(227.0);
        button.getStyleClass().add("left-select-project-button");

        // Круг с тенью
        Circle circle = new Circle(5.0);
        circle.setFill(Color.web("#8b5cf6"));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(0.0);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(16.95);
        dropShadow.setRadius(8.5275);
        dropShadow.setSpread(0.2);
        dropShadow.setWidth(19.16);
        dropShadow.setColor(Color.color(0.6139619946479797, 0.4597953259944916, 0.9736841917037964));
        circle.setEffect(dropShadow);

        button.setGraphic(circle);
        button.setPadding(new Insets(0, 0, 0, 15.0)); // left=15
        VBox.setMargin(button, new Insets(0, 0, 10, 0)); // bottom=10

        return button;
    }
}
