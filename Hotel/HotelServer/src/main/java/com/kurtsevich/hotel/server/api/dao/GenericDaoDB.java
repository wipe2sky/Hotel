package com.kurtsevich.hotel.server.api.dao;

import com.kurtsevich.hotel.server.db_model.AEntityDB;

import java.util.List;

public interface GenericDaoDB<T extends AEntityDB> {

    T getById(Integer id);

    List<T> getAll();

    void save(T entity);

    void delete(T entity);

    void update(T entity);
}
