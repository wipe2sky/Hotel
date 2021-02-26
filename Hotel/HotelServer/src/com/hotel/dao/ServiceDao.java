package com.hotel.dao;

import com.hotel.api.dao.IServiceDao;
import com.hotel.model.Service;

public class ServiceDao extends AbstractDao<Service> implements IServiceDao {
    private static ServiceDao instance;

    private ServiceDao() {
    }

    public static ServiceDao getInstance() {
        if(instance == null) instance = new ServiceDao();
        return instance;
    }

    @Override
    public Service update(Service entity) {
        Service service = getById(entity.getId());
        service.setName(entity.getName());
        service.setPrice(entity.getPrice());
        return service;
    }
}
