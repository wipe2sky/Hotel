package com.kurtsevich.hotel.server.util;


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
//private static final String userName = "root";
//    private static final String password = "6250e208";
//    private static final String url = "jdbc:mysql://localhost:3306/hoteldb";
    @Getter
    private Connection connection;

    public DBConnection() {
    }

    public void open() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, userName, password);
            }
        } catch (SQLException e) {
            //TODO Logger
            throw new RuntimeException("DB connection failed", e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            //TODO Logger
            throw new RuntimeException("Close DB connection failed", e);
        }
    }
}
