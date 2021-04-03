package com.kurtsevich.hotel.server.api.dao;

import com.kurtsevich.hotel.server.model.Service;

import java.util.List;

public interface IServiceDao extends GenericDao<Service> {
    List<Service> getSortByPrice();
}
