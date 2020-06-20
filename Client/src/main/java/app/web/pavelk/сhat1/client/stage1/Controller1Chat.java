package app.web.pavelk.сhat1.client.stage1;

import app.web.pavelk.сhat1.client.stage2.Stage2Client;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller1Chat implements Initializable {
    public TextField msgField;
    public TextArea chatArea;
    public HBox bottomPanel;
    public VBox upperPanel;
    public TextField loginField;
    public PasswordField passwordField;
    public ListView<String> clientsList;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;
    private boolean isAuthorized;
    List<TextArea> textAreas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
        textAreas = new ArrayList<>();
        textAreas.add(chatArea);
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientsList.setVisible(false);
            clientsList.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientsList.setVisible(true);
            clientsList.setManaged(true);
        }
    }

    public void authorization() {
        Thread thread = new Thread(() -> {
            if (connect()) {
                try {
                    out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
                    loginField.clear();
                    passwordField.clear();
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/authok")) {
                            Controller1Chat.this.setAuthorized(true);
                            work();
                            break;
                        } else {
                            for (TextArea o : textAreas) {
                                o.appendText(str + "\n");
                            }
                        }
                    }
                } catch (IOException e) {
                    chatArea.appendText("ошибка авторизации\n");
                    e.printStackTrace();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    public boolean connect() {
        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket(IP_ADDRESS, PORT);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                chatArea.appendText("ошибка подключения к серверу\n");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void work() {
        try {
            while (true) {
                String str = in.readUTF();
                if (str.startsWith("/")) {
                    if (str.equals("/serverclosed")) break;
                    if (str.startsWith("/clientslist ")) {
                        String[] tokens = str.split(" ");
                        Platform.runLater(() -> {
                            clientsList.getItems().clear();
                            for (int i = 1; i < tokens.length; i++) {
                                clientsList.getItems().add(tokens[i]);
                            }
                        });
                    }
                } else {
                    chatArea.appendText(str + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Controller1Chat.this.setAuthorized(false);
        }
    }

    public void sendMessage() {
        try {
            out.writeUTF(msgField.getText());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Stage2Client stage2Client = new Stage2Client(clientsList.getSelectionModel().getSelectedItem(), out, textAreas);
            stage2Client.show();
        }
    }
}

