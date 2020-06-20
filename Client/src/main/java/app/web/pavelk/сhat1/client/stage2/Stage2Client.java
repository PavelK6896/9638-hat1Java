package app.web.pavelk.сhat1.client.stage2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Stage2Client extends Stage {
    String nickTo;
    DataOutputStream out;
    List<TextArea> parentList;

    public Stage2Client(String nickTo, DataOutputStream out, List<TextArea> parentList) {
        this.nickTo = nickTo;
        this.out = out;
        this.parentList = parentList;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("stage2.fxml"));
            setTitle("personal window");
            Scene scene = new Scene(root, 300, 400);
            setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
