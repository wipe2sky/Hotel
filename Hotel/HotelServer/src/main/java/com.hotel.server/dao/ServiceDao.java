package com.hotel.server.dao;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.PostConstruct;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.api.dao.IServiceDao;
import com.hotel.server.exceptions.DaoException;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.model.Guest;
import com.hotel.server.model.Service;
import com.hotel.server.util.Logger;
import com.hotel.server.util.SerializationHandler;
@Singleton
public class ServiceDao extends AbstractDao<Service> implements IServiceDao {
    @InjectByType
    public ServiceDao(SerializationHandler serializationHandler) {
        try {
            repository.addAll(serializationHandler.deserialize(Service.class));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed");
        }
    }

//    @PostConstruct
//    public void init(){
//        try {
//            repository.addAll(SerializationHandler.deserialize(Service.class));
//        } catch (ServiceException e) {
//            logger.log(Logger.Level.WARNING, "Deserialization failed");
//        }
//    }

    @Override
    public Service update(Service entity) {
        try {
            Service service = getById(entity.getId());
            service.setName(entity.getName());
            service.setPrice(entity.getPrice());
            return service;
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Service update failed");
            throw new DaoException("Service update failed", e);
        }
    }
}
