package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ServiceDao extends AbstractDao<Service> implements IServiceDao {

    @InjectByType
    public ServiceDao(DBConnection connection) {
        this.connection = connection;
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
            //TODo
            e.printStackTrace();
        }
        return services;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, Service entity) {
        try {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, Service entity) {
        try {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getId());
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    protected String getTableName() {
        return "service";
    }


}
