module client {
    requires javafx.controls;
    requires javafx.fxml;

    opens app.web.pavelk.сhat1.client.stage1 to javafx.fxml;
    opens app.web.pavelk.сhat1.client.stage2 to javafx.fxml;


    exports app.web.pavelk.сhat1.client.stage1;
    exports app.web.pavelk.сhat1.client.stage2;
}