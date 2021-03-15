package com.hotel.server.dao;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.PostConstruct;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.api.dao.IGuestDao;
import com.hotel.server.exceptions.DaoException;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.model.Guest;
import com.hotel.server.util.Logger;
import com.hotel.server.util.SerializationHandler;

@Singleton
public class GuestDao extends AbstractDao<Guest> implements IGuestDao {

    @InjectByType
    public GuestDao(SerializationHandler serializationHandler) {
        try {
            repository.addAll(serializationHandler.deserialize(Guest.class));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed");
        }
    }

    //
//    public GuestDao() {
//    }
//    @PostConstruct
//    public void init() {
//        try {
//            repository.addAll(serializationHandler.deserialize(Guest.class));
//        } catch (ServiceException e) {
//            logger.log(Logger.Level.WARNING, "Deserialization failed");
//        }
//    }

    @Override
    public Guest update(Guest entity) {
        try {
            Guest guest = getById(entity.getId());

            guest.setFirstName(entity.getFirstName());
            guest.setLastName(entity.getLastName());
            guest.setRoom(entity.getRoom());
            guest.setLastHistory(entity.getLastHistory());
            guest.setCheckIn(entity.isCheckIn());
            guest.setServices(entity.getServices());
            guest.setHistories(entity.getHistories());
            return guest;
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Guest update failed");
            throw new DaoException("Guest update failed", e);
        }
    }


}
