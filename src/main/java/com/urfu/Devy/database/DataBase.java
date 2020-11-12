package main.java.com.urfu.Devy.database;

import java.sql.*;

public class DataBase {

    private final Connection connection;

    public DataBase(String url, String username, String password) throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://" + url + "?serverTimezone=Europe/Moscow&useSSL=false",
                username,
                password);
    }

    public Connection getConnection() {
        return connection;
    }

    public Boolean isConnected() {
        return connection != null;
    }

}
