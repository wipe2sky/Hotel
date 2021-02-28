package com.hotel.api.dao;

import com.hotel.model.AEntity;

import java.util.List;

public interface GenericDao<T extends AEntity> {

    void save(T entity);

    T getById(Integer id);

    List<T> getAll();

    void delete(T entity);

    void deleteById(Integer id);

    T update(T entity);
}
