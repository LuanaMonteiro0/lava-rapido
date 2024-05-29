package com.example.lavarapido.usecases.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection; //variável precisa ser estática
    private static PreparedStatement statement; //variável precisa ser estática

    public static Connection createConnection() {
        try {
            if (connection == null)
                connection = DriverManager.getConnection("jdbc:sqlite:lavarapido.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static PreparedStatement createPreparedStatement(String sql) {
        try {
            statement = createConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

}