package com.dao;

import com.api.dao.IServiceDao;
import com.model.Service;

public class ServiceDao extends AbstractDao<Service> implements IServiceDao {
    @Override
    public Service update(Service entity) {
        Service service = getById(entity.getId());
        service.setName(entity.getName());
        service.setPrice(entity.getPrice());
        return service;
    }
}
