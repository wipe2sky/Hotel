package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.SerializationHandler;

@Singleton
public class ServiceDao extends AbstractDao<Service> implements IServiceDao {

    @InjectByType
    public ServiceDao(SerializationHandler serializationHandler) {
        deserialize(serializationHandler);

    }

    private void deserialize(SerializationHandler serializationHandler) {
        try {
            repository.addAll(serializationHandler.deserialize(Service.class));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed", e);
            throw new RuntimeException("Deserialization failed", e);
        }
    }


    @Override
    public Service update(Service entity) {
        try {
            Service service = getById(entity.getId());
            service.setName(entity.getName());
            service.setPrice(entity.getPrice());
            return service;
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Service update failed",e);
            throw new DaoException("Service update failed", e);
        }
    }
}
