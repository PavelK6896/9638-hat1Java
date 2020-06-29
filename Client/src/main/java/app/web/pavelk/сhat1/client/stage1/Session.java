package app.web.pavelk.сhat1.client.stage1;

import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Session {

    private boolean active = false;
    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;
    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;
    public List<TextArea> textAreas;
    public ExecutorService executorService;

    public Session() {
        executorService = Executors.newFixedThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("tow thread");
            return thread;
        });
        textAreas = new ArrayList<>();
    }

    public boolean connect() {

        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket(IP_ADDRESS, PORT);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {

                e.printStackTrace();
                return false;
            }
        }
        return true;


    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
