package main.java.com.urfu.Devy.database;

import java.sql.*;
import java.util.Properties;

public class DataBase {

    private final Connection connection;

    public DataBase(String url, String username, String password) throws Exception {
        var properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        properties.put("serverTimezone", "Europe/Moscow");
        properties.put("useSSL", "false");
        properties.put("characterEncoding","cp1251");
        connection = DriverManager.getConnection("jdbc:mysql://" + url, properties);
    }

    public Connection getConnection() {
        return connection;
    }

    public Boolean isConnected() {
        return connection != null;
    }

}
