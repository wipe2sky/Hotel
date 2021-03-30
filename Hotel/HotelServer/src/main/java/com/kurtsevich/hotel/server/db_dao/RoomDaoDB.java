package com.kurtsevich.hotel.server.db_dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IRoomDaoDB;
import com.kurtsevich.hotel.server.db_model.RoomDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomDaoDB extends AbstractDaoDB<RoomDB> implements IRoomDaoDB {

    @InjectByType
    public RoomDaoDB(DBConnection connection) {
        this.connection = connection;
        insertNew = "INSERT INTO room(number, capacity, stars, price) VALUES(?,?,?,?)";
        updateString = "UPDATE room SET number=?,capacity=?,stars=?,price=?,status=?,guests_in_room=?,is_cleaning=? WHERE id=?";
    }

    @Override
    protected List<RoomDB> parseFromResultSet(ResultSet resultSet) {
        List<RoomDB> rooms = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer number = resultSet.getInt("number");
                Integer capacity = resultSet.getInt("capacity");
                Integer stars = resultSet.getInt("stars");
                Double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");
                Integer guestsInRoom = resultSet.getInt("guests_in_room");
                Boolean isCleaning = resultSet.getBoolean("is_cleaning");
                rooms.add(new RoomDB(id, number, capacity, stars, price, status, guestsInRoom, isCleaning));
            }
        } catch (SQLException e) {
            //TODo
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, RoomDB entity) {
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
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, RoomDB entity) {
        try {
            preparedStatement.setInt(1, entity.getNumber());
            preparedStatement.setInt(2, entity.getCapacity());
            preparedStatement.setInt(3, entity.getStars());
            preparedStatement.setDouble(4, entity.getPrice());
            preparedStatement.setString(5, entity.getStatus());
            preparedStatement.setInt(6, entity.getGuestsInRoom());
            preparedStatement.setBoolean(7, entity.getIsCleaning());
            preparedStatement.setInt(8, entity.getId());

        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    protected String getTableName() {
        return "room";
    }

//    @Override
//    protected String getTableValues(RoomDB entity) {
//        String result = String.format("status='%s', guests_in_room=%d, is_cleaning=%b",
//                entity.getStatus(), entity.getGuestsInRoom(), entity.getIsCleaning());
//        System.out.println(result);
//        return result;
//    }
}
