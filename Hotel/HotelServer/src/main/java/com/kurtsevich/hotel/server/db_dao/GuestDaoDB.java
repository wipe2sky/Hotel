package com.kurtsevich.hotel.server.db_dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDaoDB;
import com.kurtsevich.hotel.server.db_model.GuestDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GuestDaoDB extends AbstractDaoDB<GuestDB> implements IGuestDaoDB {

    @InjectByType
    public GuestDaoDB(DBConnection connection) {
        this.connection = connection;
        insertNew = "INSERT INTO guest(last_name, first_name) VALUES(?,?)";
        updateString = "UPDATE guest SET last_name=?,first_name=?,is_check_in=? WHERE id=?";
    }

    @Override
    protected List<GuestDB> parseFromResultSet(ResultSet resultSet) {
        List<GuestDB> guests = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                boolean isCheckIn = resultSet.getBoolean("is_check_in");
                guests.add(new GuestDB(id, lastName, firstName, isCheckIn));
            }
        } catch (SQLException e) {
            //TODo
            e.printStackTrace();
        }


        return guests;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, GuestDB entity) {
        try {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, GuestDB entity) {
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

//    @Override
//    protected String getTableValues(GuestDB entity) {
//        return String.format("last_name='%s', first_name='%s', is_check_in=%b",
//                entity.getLastName(), entity.getFirstName(), entity.isCheckIn());
//
//    }
}


