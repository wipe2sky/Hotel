package com.kurtsevich.hotel.server.db_dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDaoDB;
import com.kurtsevich.hotel.server.db_model.GuestDB;
import com.kurtsevich.hotel.server.db_model.HistoryDB;
import com.kurtsevich.hotel.server.db_model.RoomDB;
import com.kurtsevich.hotel.server.db_model.ServiceDB;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.util.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class HistoryDaoDB extends AbstractDaoDB<HistoryDB> implements IHistoryDaoDB {
    private RoomDaoDB roomDaoDB;
    private GuestDaoDB guestDaoDB;
    private ServiceDaoDB serviceDaoDB;

    @InjectByType
    public HistoryDaoDB(DBConnection connection, RoomDaoDB roomDaoDB, GuestDaoDB guestDaoDB, ServiceDaoDB serviceDaoDB) {
        this.connection = connection;
        this.roomDaoDB = roomDaoDB;
        this.guestDaoDB = guestDaoDB;
        this.serviceDaoDB = serviceDaoDB;
        insertNew = "INSERT INTO history(check_in_date, check_out_date, cost_of_living, room_id, guest_id) VALUES(?,?,?,?,?)";
        updateString = "UPDATE history SET check_in_date=?,check_out_date=?,cost_of_living=?,cost_of_service=?,room_id=?,guest_id=? WHERE id=?";
    }

    @Override
    protected List<HistoryDB> parseFromResultSet(ResultSet resultSet) {
        List<HistoryDB> histories = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                LocalDate checkInDate = resultSet.getDate("check_in_date").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("check_out_date").toLocalDate();
                Double costOfLiving = resultSet.getDouble("cost_of_living");
                Double costOfService = resultSet.getDouble("cost_of_service");
                RoomDB room = roomDaoDB.getById(resultSet.getInt("room_id"));
                GuestDB guest = guestDaoDB.getById(resultSet.getInt("guest_id"));
                List<ServiceDB> services = getServicesById(id);


                histories.add(new HistoryDB(id, checkInDate, checkOutDate, costOfLiving, costOfService, room, guest, services));
            }
        } catch (SQLException e) {
            //TODo
            e.printStackTrace();
        }


        return histories;
    }

    private List<ServiceDB> getServicesById(Integer id) {
        List<ServiceDB> entities = new ArrayList<>();
        try (Statement statement = connection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format("SELECT service_id FROM history_service WHERE history_id=%d", id));
            while (resultSet.next()){
                entities.add(serviceDaoDB.getById(resultSet.getInt(1)));

            }
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
        return entities;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, HistoryDB entity) {
        try {
            preparedStatement.setDate(1, Date.valueOf(entity.getCheckInDate()));
            preparedStatement.setDate(2, Date.valueOf(entity.getCheckOutDate()));
            preparedStatement.setDouble(3, entity.getCostOfLiving());
            preparedStatement.setInt(4, entity.getRoom().getId());
            preparedStatement.setInt(5, entity.getGuest().getId());
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, HistoryDB entity) {
        try {
            preparedStatement.setDate(1, Date.valueOf(entity.getCheckInDate()));
            preparedStatement.setDate(2, Date.valueOf(entity.getCheckOutDate()));
            preparedStatement.setDouble(3, entity.getCostOfLiving());
            preparedStatement.setDouble(4, entity.getCostOfService());
            preparedStatement.setInt(5, entity.getRoom().getId());
            preparedStatement.setInt(6, entity.getGuest().getId());
            preparedStatement.setInt(7, entity.getId());
            if(!entity.getServices().isEmpty()) {
                updateServiceToGuest(entity);
            }
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

    private void deleteHistoryServiceValue(HistoryDB entity){
        Integer historyId = entity.getId();

        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement("DELETE FROM history_service WHERE history_id=?")) {
            preparedStatement.setInt(1, historyId);
            preparedStatement.execute();
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
    }

    private void updateServiceToGuest(HistoryDB entity) {
        Integer historyId = entity.getId();
        List<ServiceDB> services = entity.getServices();
        //Удаляем из соеденительной базы все предыдущие записи с этой историей
        deleteHistoryServiceValue(entity);

        //Сетим актуальные данные
        try (Statement statement = connection.getConnection().createStatement()) {
            for (ServiceDB service : services) {
                statement.execute(String.format( "INSERT INTO history_service VALUES(%d, %d)", historyId, service.getId()));
            }
        } catch (SQLException e) {
            //TODO
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: ");
            throw new DaoException("Couldn't find entity by id: ", e);
        }
    }


    @Override
    protected String getTableName() {
        return "history";
    }

//    @Override
//    protected String getTableValues(HistoryDB entity) {
//        return String.format("last_name='%s', first_name='%s', is_check_in=%b",
//                entity.getLastName(), entity.getFirstName(), entity.isCheckIn());
//
//    }
}
