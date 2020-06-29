package app.web.pavelk.сhat1.client.stage1;

import java.io.IOException;

public class Controller1About {

    Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public void stage1Chat() {
        try {
            Stage1Chat.setFXML("stage1Chat", session);
            session.out.writeUTF("/start1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

