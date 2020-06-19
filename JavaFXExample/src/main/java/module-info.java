module client {
    requires javafx.controls;
    requires javafx.fxml;

    opens ex2 to javafx.fxml;

    exports ex1;
    exports ex2;
}