module mryazik.github.io {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;
    requires com.google.common;
    requires com.fasterxml.jackson.databind;

    // ОБЯЗАТЕЛЬНО: Открываем пакет с главным классом для javafx.graphics
    // Без этого JavaFX не сможет создать экземпляр вашего App
    opens mryazik.github.io to javafx.graphics, javafx.fxml;
    // Открываем контроллеры для FXMLLoader
    opens mryazik.github.io.Controllers to javafx.fxml;
    opens mryazik.github.io.Classes to javafx.fxml;
    opens mryazik.github.io.workData to com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.annotation;



    exports mryazik.github.io;
}