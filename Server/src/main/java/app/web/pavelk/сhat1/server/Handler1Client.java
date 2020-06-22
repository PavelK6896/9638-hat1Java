package app.web.pavelk.сhat1.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Handler1Client {
    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;
    List<String> blackList;

    public Handler1Client(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
        this.blackList = new ArrayList<>();
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        working();
    }

    public void working() {
        new Thread(() -> {
            try {
                while (true) {
                    String str = in.readUTF();
                    if (str.startsWith("/auth")) {
                        String[] tokens = str.split(" ");
                        String newNick = Service1Authorization.getNickByLoginAndPass(tokens[1], tokens[2]);
                        if (newNick != null) {
                            if (!server.isNickBusy(newNick)) {
                                System.out.println("клиент авторизовался");
                                sendMessage("/authok");
                                nick = newNick;
                                server.subscribe(this);
                                continue;
                            } else {
                                sendMessage("Учетная запись уже используется");
                                continue;
                            }
                        } else {
                            sendMessage("Неверный логин/пароль");
                            continue;
                        }
                    }

                    if (str.startsWith("/")) {
                        if (str.equals("/end")) {
                            out.writeUTF("/serverclosed");
                            break;
                        }
                        if (str.startsWith("/w ")) {
                            String[] tokens = str.split(" ", 3);
                            server.sendPersonalMessage(this, tokens[1], tokens[2]);
                        }
                        if (str.startsWith("/blacklist ")) {
                            String[] tokens = str.split(" ");
                            blackList.add(tokens[1]);
                            sendMessage("Вы добавили пользователя " + tokens[1] + " в черный список");
                        }
                    } else {
                        server.broadcastMsg(this, nick + ": " + str);
                    }
                    System.out.println("Client: " + str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server.unsubscribe(this);
            }
        }).start();
    }

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
