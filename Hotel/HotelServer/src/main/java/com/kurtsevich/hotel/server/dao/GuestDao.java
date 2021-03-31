package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GuestDao extends AbstractDao<Guest> implements IGuestDao {

    @InjectByType
    public GuestDao(DBConnection connection) {
        this.connection = connection;
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
            //TODo
            e.printStackTrace();
        }


        return guests;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, Guest entity) {
        try {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
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
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    protected String getTableName() {
        return "guest";
    }


}


