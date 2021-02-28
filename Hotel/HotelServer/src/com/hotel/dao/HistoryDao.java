package com.hotel.dao;

import com.hotel.api.dao.IHistoryDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.Guest;
import com.hotel.model.History;
import com.hotel.util.Logger;
import com.hotel.util.SerializationHandler;

public class HistoryDao extends AbstractDao<History> implements IHistoryDao {
    private static HistoryDao instance;

    private HistoryDao() {
    }

    public static HistoryDao getInstance() {
        if (instance == null) {
            instance = new HistoryDao();
            try {
                instance.repository.addAll(SerializationHandler.deserialize(History.class));
            } catch (ServiceException e) {
                instance.logger.log(Logger.Level.WARNING, "Deserialization failed");
            }
        }
        return instance;
    }

    @Override
    public History update(History entity) {
        try {
            History history = getById(entity.getId());
            history.setRoom(entity.getRoom());
            history.setGuest(entity.getGuest());
            history.setCheckInDate(entity.getCheckInDate());
            history.setCheckOutDate(entity.getCheckOutDate());
            history.setCostOfLiving(entity.getCostOfLiving());
            history.setServices(entity.getServices());
            return history;
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "History update failed");
            throw new DaoException("History update failed", e);
        }
    }
}
