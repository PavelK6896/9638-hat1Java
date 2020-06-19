module client {
    requires javafx.controls;
    requires javafx.fxml;

    opens app.web.pavelk.сhat1.client to javafx.fxml;
    exports app.web.pavelk.сhat1.client;
}