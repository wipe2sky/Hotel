package com.kurtsevich.hotel.server.util;


import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.Singleton;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DBConnector {
    private final Logger logger = LoggerFactory.getLogger(DBConnector.class);
    @ConfigProperty
    private String userName;
    @ConfigProperty
    private String password;
    @ConfigProperty
    private String url;

    @Getter
    private Connection connection;

    public DBConnector() {
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

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Impossible disable autocommit", e);
            rollback();

        }
    }

    public void finishTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Commit to DB failed", e);
            rollback();
        }
    }

    public void rollback() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Rollback DB chang failed", e);
            throw new RuntimeException("Rollback DB chang failed", e);
        }
    }
}
