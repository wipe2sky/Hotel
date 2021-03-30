package com.kurtsevich.hotel.server.api.dao;

import com.kurtsevich.hotel.server.model.AEntity;

import java.util.List;

public interface GenericDao<T extends AEntity> {
    void save(T entity);

    T getById(Integer id);

    List<T> getAll();

    void delete(T entity);

    T update(T entity);
}
