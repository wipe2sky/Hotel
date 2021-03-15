package com.hotel.server.dao;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.PostConstruct;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.api.dao.IHistoryDao;
import com.hotel.server.exceptions.DaoException;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.model.Guest;
import com.hotel.server.model.History;
import com.hotel.server.util.Logger;
import com.hotel.server.util.SerializationHandler;
@Singleton
public class HistoryDao extends AbstractDao<History> implements IHistoryDao {
@InjectByType
    public HistoryDao(SerializationHandler serializationHandler) {
    try {
        repository.addAll(serializationHandler.deserialize(History.class));
    } catch (ServiceException e) {
        logger.log(Logger.Level.WARNING, "Deserialization failed");
    }
    }

//    @PostConstruct
//    public void init(){
//        try {
//            repository.addAll(SerializationHandler.deserialize(History.class));
//        } catch (ServiceException e) {
//            logger.log(Logger.Level.WARNING, "Deserialization failed");
//        }
//    }

//    public static HistoryDao getInstance() {
//        if (instance == null) {
//            instance = new HistoryDao();
//            try {
//                instance.repository.addAll(SerializationHandler.deserialize(History.class));
//            } catch (ServiceException e) {
//                instance.logger.log(Logger.Level.WARNING, "Deserialization failed");
//            }
//        }
//        return instance;
//    }

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
