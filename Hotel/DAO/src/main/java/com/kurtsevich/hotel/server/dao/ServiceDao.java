package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ServiceDao extends AbstractDao<Service> implements IServiceDao {
    private final Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    @InjectByType
    public ServiceDao(DBConnector connection) {
        this.connector = connection;
        insertNew = "INSERT INTO service(name, price) VALUES(?,?)";
        updateString = "UPDATE service SET name=?,price=? WHERE id=?";
    }

    @Override
    protected List<Service> parseFromResultSet(ResultSet resultSet) {
        List<Service> services = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                services.add(new Service(id, name, price));
            }
        } catch (SQLException e) {
            logger.warn("Couldn't parse from result", e);
            throw new DaoException("Couldn't parse from result", e);
        }
        return services;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, Service entity) {
        try {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
        } catch (SQLException e) {
            logger.warn("Couldn't fill prepared statement", e);
            throw new DaoException("Couldn't fill prepared statement", e);
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, Service entity) {
        try {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getId());
        } catch (SQLException e) {
            logger.warn("Couldn't set prepared statement", e);
            throw new DaoException("Couldn't set prepared statement", e);
        }
    }

    @Override
    protected String getTableName() {
        return "service";
    }

    @Override
    public List<Service> getSortByPrice() {
        List<Service> entities = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM service ORDER BY price;");
            entities.addAll(parseFromResultSet(resultSet));
        } catch (SQLException e) {
            logger.warn("Couldn't read from DB ", e);
            throw new DaoException("Couldn't read from DB", e);
        }
        return entities;
    }
}
