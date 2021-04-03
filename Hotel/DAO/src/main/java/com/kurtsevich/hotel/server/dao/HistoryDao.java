package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class HistoryDao extends AbstractDao<History> implements IHistoryDao {
    private final Logger logger = LoggerFactory.getLogger(HistoryDao.class);
    private static final String NOT_PARSE = "Couldn't parse from result";
    private static final String NOT_GET_SERVICE = "Couldn't get services by id";
    private static final String NOT_FILL = "Couldn't fill prepared statement";
    private static final String NOT_SET_STATEMENT = "Couldn't set prepared statement";
    private static final String NOT_DELETE = "Couldn't delete value from history_service table";
    private static final String NOT_SET_VALUE = "Couldn't set value to history_service table";
    private static final String NOT_GET_VALUE = "Couldn't get value from history table";
    private final RoomDao roomDaoDB;
    private final GuestDao guestDaoDB;
    private final ServiceDao serviceDaoDB;

    @InjectByType
    public HistoryDao(DBConnector connection, RoomDao roomDaoDB, GuestDao guestDaoDB, ServiceDao serviceDaoDB) {
        this.connector = connection;
        this.roomDaoDB = roomDaoDB;
        this.guestDaoDB = guestDaoDB;
        this.serviceDaoDB = serviceDaoDB;
        insertNew = "INSERT INTO history(check_in_date, check_out_date, cost_of_living, room_id, guest_id) VALUES(?,?,?,?,?)";
        updateString = "UPDATE history SET check_in_date=?,check_out_date=?,cost_of_living=?,cost_of_service=?,room_id=?,guest_id=? WHERE id=?";
    }

    @Override
    protected List<History> parseFromResultSet(ResultSet resultSet) {
        List<History> histories = new ArrayList<>();
        try {
            while (resultSet.next()) {
                histories.add(resultSetMapper(resultSet));
            }
        } catch (SQLException e) {
            logger.warn(NOT_PARSE, e);
            throw new DaoException(NOT_PARSE, e);
        }


        return histories;
    }

    private History resultSetMapper(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        LocalDateTime checkInDate = resultSet.getTimestamp("check_in_date").toLocalDateTime();
        LocalDateTime checkOutDate = resultSet.getTimestamp("check_out_date").toLocalDateTime();
        Double costOfLiving = resultSet.getDouble("cost_of_living");
        Double costOfService = resultSet.getDouble("cost_of_service");
        Room room = roomDaoDB.getById(resultSet.getInt("room_id"));
        Guest guest = guestDaoDB.getById(resultSet.getInt("guest_id"));
        List<Service> services = getServicesById(id);


        return new History(id, checkInDate, checkOutDate, costOfLiving, costOfService, room, guest, services);
    }

    private List<Service> getServicesById(Integer id) {
        List<Service> entities = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format("SELECT service_id FROM history_service WHERE history_id=%d", id));
            while (resultSet.next()) {
                entities.add(serviceDaoDB.getById(resultSet.getInt(1)));

            }
        } catch (SQLException e) {
            logger.warn(NOT_GET_SERVICE, e);
            throw new DaoException(NOT_GET_SERVICE, e);
        }
        return entities;
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, History entity) {
        try {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(entity.getCheckInDate()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getCheckOutDate()));
            preparedStatement.setDouble(3, entity.getCostOfLiving());
            preparedStatement.setInt(4, entity.getRoom().getId());
            preparedStatement.setInt(5, entity.getGuest().getId());
        } catch (SQLException e) {
            logger.warn(NOT_FILL, e);
            throw new DaoException(NOT_FILL, e);
        }
    }

    @Override
    protected void fillAllPreparedStatement(PreparedStatement preparedStatement, History entity) {
        try {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(entity.getCheckInDate()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getCheckOutDate()));
            preparedStatement.setDouble(3, entity.getCostOfLiving());
            preparedStatement.setDouble(4, entity.getCostOfService());
            preparedStatement.setInt(5, entity.getRoom().getId());
            preparedStatement.setInt(6, entity.getGuest().getId());
            preparedStatement.setInt(7, entity.getId());
            if (!entity.getServices().isEmpty()) {
                updateServiceToGuest(entity);
            }
        } catch (SQLException e) {
            logger.warn(NOT_SET_STATEMENT, e);
            throw new DaoException(NOT_SET_STATEMENT, e);
        }
    }

    private void deleteHistoryServiceValue(History entity) {
        Integer historyId = entity.getId();

        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement("DELETE FROM history_service WHERE history_id=?")) {
            preparedStatement.setInt(1, historyId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.warn(NOT_DELETE, e);
            throw new DaoException(NOT_DELETE, e);
        }
    }

    private void updateServiceToGuest(History entity) {
        Integer historyId = entity.getId();
        List<Service> services = entity.getServices();
        //Удаляем из соеденительной базы все предыдущие записи с этой историей
        deleteHistoryServiceValue(entity);

        //Сетим актуальные данные
        try (Statement statement = connector.getConnection().createStatement()) {
            for (Service service : services) {
                statement.execute(String.format("INSERT INTO history_service VALUES(%d, %d)", historyId, service.getId()));
            }
        } catch (SQLException e) {
            logger.warn(NOT_SET_VALUE, e);
            throw new DaoException(NOT_SET_VALUE, e);
        }
    }


    @Override
    protected String getTableName() {
        return "history";
    }

    public List<History> getByGuest(Guest entity) {
        List<History> histories;
        List<History> entities = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SElECT * FROM history WHERE guest_id=" + entity.getId());
            entities.addAll(parseFromResultSet(resultSet));
            histories = entities.stream()
                    .sorted(Comparator.comparing(History::getCheckOutDate).reversed())
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            logger.warn(NOT_GET_VALUE, e);
            throw new DaoException(NOT_GET_VALUE, e);
        }
        return histories;
    }

    public List<History> getByRoom(Room entity) {
        List<History> histories;
        List<History> entities = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SElECT * FROM history WHERE room_id=" + entity.getId());
            entities.addAll(parseFromResultSet(resultSet));
            histories = entities.stream()
                    .sorted(Comparator.comparing(History::getCheckOutDate).reversed())
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            logger.warn(NOT_GET_VALUE, e);
            throw new DaoException(NOT_GET_VALUE, e);
        }
        return histories;
    }
}