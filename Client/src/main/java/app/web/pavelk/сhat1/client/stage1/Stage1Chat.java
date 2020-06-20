package app.web.pavelk.сhat1.client.stage1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Stage1Chat extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Stage1Chat.class.getResource("stage1Chat.fxml"));
        scene = new Scene(root);
        setCss("styles1");
        primaryStage.getIcons().add(new Image(Stage1Chat.class.getResourceAsStream("chat.png")));
        primaryStage.setTitle("Chat1");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Close");
        });
    }

    static void setCss(String css) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Stage1Chat.class.getResource(css + ".css").toExternalForm());
    }

    static void setFXML(String fxml) throws IOException {
        scene.setRoot( FXMLLoader.load(Stage1Chat.class.getResource(fxml + ".fxml")));
    }


    public static void main(String[] args) {
        launch(args);
    }
}