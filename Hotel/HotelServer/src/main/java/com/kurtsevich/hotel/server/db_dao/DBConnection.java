package com.kurtsevich.hotel.server.db_dao;


import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.Singleton;
import lombok.Getter;

import java.sql.*;

@Singleton
public class DBConnection {
    @ConfigProperty
    private String userName;
    @ConfigProperty
    private String password;
    @ConfigProperty
    private String url;
    @Getter
    private Connection connection;

    public DBConnection() {
        open();
    }

    public void open() {
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, userName, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB connection failed", e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Close DB connection failed", e);
        }
    }
}
