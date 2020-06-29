package app.web.pavelk.сhat1.client.stage1;

import app.web.pavelk.сhat1.client.stage2.Stage2Client;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller1Chat implements Initializable {
    public TextField msgField;
    public TextArea workTextArea;
    public HBox bottomPanel;
    public VBox authorizationVBox;
    public TextField loginField;
    public PasswordField passwordField;
    public ListView<String> clientsList;
    public CheckMenuItem CheckMenuItemWhite;
    public CheckMenuItem CheckMenuItemBlack;
    public HBox workHBox;
    public VBox basicVBox;
    public Text authorizationText;
    public boolean work = true;

    public Session session;


    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void setAuthorized(boolean isAuthorized) {
        if (!isAuthorized) {
            authorizationVBox.setVisible(true);
            authorizationVBox.setManaged(true);
            workHBox.setVisible(false);
            workHBox.setManaged(false);

        } else {
            authorizationVBox.setVisible(false);
            authorizationVBox.setManaged(false);
            workHBox.setVisible(true);
            workHBox.setManaged(true);
            Stage stage = (Stage) workHBox.getScene().getWindow();
            stage.setWidth(300);
            stage.setHeight(400);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientsList.setVisible(true);
            clientsList.setManaged(true);

            workTextArea.setPrefSize(100, 100);
            clientsList.setPrefSize(100, 100);

            System.out.println(session.socket);
            session.executorService.submit(() -> {
                work();
            });

        }
    }


    public void authorization1() {

        session.executorService.submit(() -> {

            if (session.connect()) {
                try {
                    session.out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
                    passwordField.clear();
                    loginField.clear();
                    while (true) {
                        String str = session.in.readUTF();
                        if (str.startsWith("/authok")) {
                            // textAreas.add(workTextArea);
                            session.setActive(true);
                            setAuthorized(true);
                            break;
                        } else {
                            authorizationText.setText(str + " ");
                            break;
                        }
                    }
                } catch (IOException e) {

                    authorizationText.setText("ошибка авторизации\n");

                    e.printStackTrace();
                }
            } else {
                authorizationText.setText("ошибка подключения к серверу\n");
            }
        });
    }


    public void work() {
        try {

            while (work) {

                String str = session.in.readUTF();

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
                    workTextArea.appendText(str + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        try {
            session.out.writeUTF(msgField.getText() + " " + getCurrentTime());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Stage2Client stage2Client =
                    new Stage2Client(clientsList.getSelectionModel().getSelectedItem(), session.out, session.textAreas);
            stage2Client.show();
        }
    }

    public void setBlack() {
        CheckMenuItemWhite.setSelected(false);
        CheckMenuItemBlack.setSelected(true);
        Stage1Chat.setCss("styles1");
    }

    public void setWhite() {
        CheckMenuItemWhite.setSelected(true);
        CheckMenuItemBlack.setSelected(false);
        Stage1Chat.setCss("styles2");
    }

    public void exit() {
        Platform.exit();
    }

    public void stage1About() {
        try {
            Stage1Chat.setFXML("stage1About", session);
            session.out.writeUTF("/end1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearTextArea() {
        workTextArea.clear();
    }

    public String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }


}

