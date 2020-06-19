package app.web.pavelk.сhat1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private final Vector<Handler1Client> clients;
    ServerSocket server;
    Socket socket = null;

    public Server(int port) throws IOException {
        this.clients = new Vector<>();
        this.server = new ServerSocket(port);
        System.out.println("Сервер запущен на порту: " + port);
    }

    public void start() {
        try {
            Service1Authorization.connect();
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new Handler1Client(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Service1Authorization.disconnect();
        }
    }

    public void sendPersonalMessage(Handler1Client from, String nickTo, String message) {
        for (Handler1Client h : clients) {
            if (h.getNick().equals(nickTo)) {
                h.sendMessage("from " + from.getNick() + ": " + message);
                from.sendMessage("to " + nickTo + ": " + message);
                return;
            }
        }
        from.sendMessage("Клиент с ником " + nickTo + " не найден в чате");
    }

    public void broadcastMsg(Handler1Client handler1Client, String message) {
        for (Handler1Client h : clients) {
            if (!h.checkBlackList(handler1Client.getNick())) {
                h.sendMessage(message);
            }
        }
    }

    public boolean isNickBusy(String nick) {
        for (Handler1Client h : clients) {
            if (h.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientsList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/clientslist ");
        for (Handler1Client handler1Client : clients) {
            stringBuilder.append(handler1Client.getNick()).append(" ");
        }

        for (Handler1Client handler1Client : clients) {
            handler1Client.sendMessage(stringBuilder.toString());
        }
    }

    public void subscribe(Handler1Client handler1Client) {
        clients.add(handler1Client);
        broadcastClientsList();
    }

    public void unsubscribe(Handler1Client handler1Client) {
        clients.remove(handler1Client);
        broadcastClientsList();
    }
}
