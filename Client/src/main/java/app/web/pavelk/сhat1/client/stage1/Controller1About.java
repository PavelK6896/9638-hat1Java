package app.web.pavelk.сhat1.client.stage1;

import java.io.IOException;

public class Controller1About {

    public void stage1Chat() {
        try {
            Stage1Chat.setFXML("stage1Chat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

