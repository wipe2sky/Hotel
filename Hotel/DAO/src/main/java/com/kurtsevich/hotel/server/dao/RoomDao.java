package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomDao extends AbstractDao<Room> implements IRoomDao {
    private final Logger logger = LoggerFactory.getLogger(RoomDao.class);

    @InjectByType
    public RoomDao(DBConnector connection) {
        this.connector = connection;
        insertNew = "INSERT INTO room(number, capacity, stars, price) VALUES(?,?,?,?)";
        updateString = "UPDATE room SET number=?,capacity=?,stars=?,price=?,status=?,guests_in_room=?,is_cleaning=? WHERE id=?";
    }

    @Override
    protected List<Room> parseFromResultSet(ResultSet resultSet) {
        List<Room> rooms = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer number = resultSet.getInt("number");
                Integer capacity = resultSet.getInt("capacity");
                Integer stars = resultSet.getInt("stars");
                Double price = resultSet.getDouble("price");
                RoomStatus status = RoomStatus.valueOf(resultSet.getString("status"));
                Integer guestsInRoom = resultSet.getInt("guests_in_room");
                Boolean isCleaning = resultSet.getBoolean("is_cleaning");
                rooms.add(new Room(id, number, capacity, stars, price, status, guestsInRoom, isCleaning));
            }
        } catch (SQLException e) {
            logger.warn("Couldn't parse from result", e);
            throw new DaoException("Couldn't parse from result", e);
        }
        return rooms;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, Room entity) {
        Integer number = entity.getNumber();
        Integer capacity = entity.getCapacity();
        Integer stars = entity.getStars();
        Double price = entity.getPrice();
        try {
            preparedStatement.setInt(1, number);
            preparedStatement.setInt(2, capacity);
            preparedStatement.setInt(3, stars);
            preparedStatement.setDouble(4, price);
        } catch (SQLException e) {
            logger.warn("Couldn't fill prepared statement", e);
            throw new DaoException("Couldn't fill prepared statement", e);
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, Room entity) {
        try {
            preparedStatement.setInt(1, entity.getNumber());
            preparedStatement.setInt(2, entity.getCapacity());
            preparedStatement.setInt(3, entity.getStars());
            preparedStatement.setDouble(4, entity.getPrice());
            preparedStatement.setString(5, entity.getStatus().toString());
            preparedStatement.setInt(6, entity.getGuestsInRoom());
            preparedStatement.setBoolean(7, entity.getIsCleaning());
            preparedStatement.setInt(8, entity.getId());

        } catch (SQLException e) {
            logger.warn("Couldn't set prepared statement", e);
            throw new DaoException("Couldn't set prepared statement", e);
        }
    }

    @Override
    protected String getTableName() {
        return "room";
    }


}
