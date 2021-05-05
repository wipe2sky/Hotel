package com.kurtsevich.hotel.api.dao;

import com.kurtsevich.hotel.model.Service;

import java.util.List;

public interface IServiceDao extends GenericDao<Service> {
    List<Service> getSortByPrice();
}
