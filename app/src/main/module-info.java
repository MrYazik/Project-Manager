module mryazik.github.io {
    requires javafx.controls;
    requires javafx.fxml;

    opens mryazik.github.io to javafx.fxml;

    exports mryazik.github.io;
}