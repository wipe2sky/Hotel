package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.DBConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GuestDao extends AbstractDao<Guest> implements IGuestDao {
    private final Logger logger = LoggerFactory.getLogger(GuestDao.class);
    private static final String SORT = "SELECT id, last_name, first_name, is_check_in FROM guest JOIN history USING(id) WHERE is_check_in = false AND check_out_date > CURRENT_DATE() ORDER BY %s;";

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

    public List<Guest> getSortBy(SortStatus sortStatus) {
        List<Guest> entities = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(SORT, sortStatus.getValue()));
            entities.addAll(parseFromResultSet(resultSet));
        } catch (SQLException e) {
            logger.warn("Couldn't read from DB ", e);
            throw new DaoException("Couldn't read from DB", e);
        }
        return entities;
    }
}


