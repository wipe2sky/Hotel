package com.kurtsevich.hotel.api.dao;

import com.kurtsevich.hotel.model.AEntity;

import java.util.List;

public interface GenericDao<T extends AEntity> {

    T getById(Integer id);

    List<T> getAll();

    void save(T entity);

    void delete(T entity);

    void update(T entity);

}
