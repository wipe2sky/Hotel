package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.server.api.dao.GenericDao;
import com.kurtsevich.hotel.server.model.AEntity;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.util.DBConnection;
import com.kurtsevich.hotel.server.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends AEntity> implements GenericDao<T> {
    //TODO Logger
    protected final Logger logger = new Logger(this.getClass().getName());
    protected DBConnection connection;

    protected abstract List<T> parseFromResultSet(ResultSet resultSet);

    protected String insertNew;
    private static final String GET_ALL = "SElECT * FROM %s";
    private static final String GET_BY_ID = "SElECT * FROM %s WHERE id=%d";
    private static final String DELETE_BY_ID = "DELETE FROM %s WHERE id=?";
    protected String updateString;

    protected abstract void fillPreparedStatement(PreparedStatement preparedStatement, T entity);


    @Override
    public T getById(Integer id) {
        T entity;
        try (Statement statement = connection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(GET_BY_ID, getTableName(), id));
            entity = parseFromResultSet(resultSet).get(0);
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
        return entity;
    }

    protected abstract String getTableName();

//    protected abstract String getTableValues(T entity);

    @Override
    public void save(T entity) {
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(insertNew)) {
            fillPreparedStatement(preparedStatement, entity);
            preparedStatement.execute();
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
    }


    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try (Statement statement = connection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(GET_ALL, getTableName()));
            entities.addAll(parseFromResultSet(resultSet));
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
        return entities;
    }

    @Override
    public void delete(T entity) {
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(String.format(DELETE_BY_ID, getTableName()))) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
    }

    @Override
    public void update(T entity) {
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(updateString)) {
            fillAllPreparedStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
    }

    protected abstract void fillAllPreparedStatement(PreparedStatement preparedStatement, T entity);


}
