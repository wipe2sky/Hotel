package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GuestDao extends AbstractDao<Guest> implements IGuestDao {
    private final Logger logger = LoggerFactory.getLogger(GuestDao.class);

    @InjectByType
    public GuestDao(DBConnector connection) {
        this.connector = connection;
        insertNew = "INSERT INTO guest(last_name, first_name) VALUES(?,?)";
        updateString = "UPDATE guest SET last_name=?,first_name=?,is_check_in=? WHERE id=?";
    }

    @Override
    protected List<Guest> parseFromResultSet(ResultSet resultSet) {
        List<Guest> guests = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                boolean isCheckIn = resultSet.getBoolean("is_check_in");
                guests.add(new Guest(id, lastName, firstName, isCheckIn));
            }
        } catch (SQLException e) {
            logger.warn("Couldn't parse from result", e);
            throw new DaoException("Couldn't parse from result", e);
        }


        return guests;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, Guest entity) {
        try {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
        } catch (SQLException e) {
            logger.warn("Couldn't fill prepared statement", e);
            throw new DaoException("Couldn't fill prepared statement", e);
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, Guest entity) {
        try {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setBoolean(3, entity.isCheckIn());
            preparedStatement.setInt(4, entity.getId());
        } catch (SQLException e) {
            logger.warn("Couldn't set prepared statement", e);
            throw new DaoException("Couldn't set prepared statement", e);
        }
    }

    @Override
    protected String getTableName() {
        return "guest";
    }


}


