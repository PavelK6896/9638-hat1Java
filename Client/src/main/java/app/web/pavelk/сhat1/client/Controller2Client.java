package app.web.pavelk.сhat1.client;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.DataOutputStream;
import java.io.IOException;

public class Controller2Client {
    public Button btn;
    public TextArea textArea;

    public void sendMessage() {
        DataOutputStream out = ((Stage2Client) btn.getScene().getWindow()).out;
        String nickTo = ((Stage2Client) btn.getScene().getWindow()).nickTo;
        try {
            out.writeUTF("/w " + nickTo + " " + textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.clear();
    }
}
