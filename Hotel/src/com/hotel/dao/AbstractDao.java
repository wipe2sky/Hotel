package com.hotel.dao;

import com.hotel.api.dao.GenericDao;
import com.hotel.model.AEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends AEntity> implements GenericDao<T> {
    private List<T> repository = new ArrayList<>();

    @Override
    public void save(T entity) {
        repository.add(entity);
    }

    @Override
    public T getById(Integer id) {
        for (T entity : repository
        ) {
            if (id.equals(entity.getId())) return entity;
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public void delete(T entity) {
        repository.remove(entity);
    }

    @Override
    public void deleteById(Integer id) {
        for (T entity : repository
        ) {
            if (id.equals(entity.getId())) repository.remove(entity);
        }
    }
}
