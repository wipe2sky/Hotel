package com.kurtsevich.hotel.server.util;


import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.Singleton;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

@Singleton
public class DBConnection {
    private final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    @ConfigProperty
    private String userName;
    @ConfigProperty
    private String password;
    @ConfigProperty
    private String url;

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
            logger.error("DB connection failed", e);
            throw new RuntimeException("DB connection failed", e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Close DB connection failed", e);
            throw new RuntimeException("Close DB connection failed", e);
        }
    }
}
