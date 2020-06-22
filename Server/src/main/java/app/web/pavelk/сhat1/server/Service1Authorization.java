package app.web.pavelk.сhat1.server;

import java.sql.*;

public class Service1Authorization {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Server/chat1.db");
            stmt = connection.createStatement();
            System.out.println("connect database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO main (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, pass.hashCode());
            preparedStatement.setString(3, nick);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet resultSet = stmt.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            if (resultSet.next()) {
                String nick = resultSet.getString(1);
                int dbHash = resultSet.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
