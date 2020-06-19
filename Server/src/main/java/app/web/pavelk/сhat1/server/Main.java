package app.web.pavelk.сhat1.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Server(8189).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
