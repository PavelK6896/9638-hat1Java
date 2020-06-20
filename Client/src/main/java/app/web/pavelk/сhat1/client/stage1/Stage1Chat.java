package app.web.pavelk.сhat1.client.stage1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Stage1Chat extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Stage1Chat.class.getResource("stage1.fxml"));
        scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(Stage1Chat.class.getResource("styles1.css").toExternalForm());
        primaryStage.setTitle("Chat1");
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("On Close");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}