package com.hotel.dao;

import com.hotel.api.dao.IServiceDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.Service;
import com.hotel.util.Logger;
import com.hotel.util.SerializationHandler;

public class ServiceDao extends AbstractDao<Service> implements IServiceDao {
    private static ServiceDao instance;

    private ServiceDao() {
    }

    public static ServiceDao getInstance() {
        if (instance == null) {
            instance = new ServiceDao();
            try {
                instance.repository.addAll(SerializationHandler.deserialize(Service.class));
            } catch (ServiceException e) {
                instance.logger.log(Logger.Level.WARNING, "Deserialization failed");
            }
        }
        return instance;
    }

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
