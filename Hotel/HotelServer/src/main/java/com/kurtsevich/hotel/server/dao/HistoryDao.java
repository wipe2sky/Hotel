package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.SerializationHandler;

@Singleton
public class HistoryDao extends AbstractDao<History> implements IHistoryDao {

    @InjectByType
    public HistoryDao(SerializationHandler serializationHandler) {
        deserialize(serializationHandler);

    }

    private void deserialize(SerializationHandler serializationHandler) {
        try {
            repository.addAll(serializationHandler.deserialize(History.class));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed", e);
            throw new RuntimeException("Deserialization failed", e);
        }
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
            logger.log(Logger.Level.WARNING, "History update failed", e);
            throw new DaoException("History update failed", e);
        }
    }
}
