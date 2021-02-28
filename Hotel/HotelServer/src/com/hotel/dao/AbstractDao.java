package com.hotel.dao;

import com.hotel.api.dao.GenericDao;
import com.hotel.exceptions.DaoException;
import com.hotel.model.AEntity;
import com.hotel.util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends AEntity> implements GenericDao<T>, Serializable {
    protected final Logger logger = new Logger(this.getClass().getName());
    protected List<T> repository = new ArrayList<>();



    @Override
    public void save(T entity) {
        repository.add(entity);
    }


    @Override
    public T getById(Integer id) {
        for (T entity :
                repository) {
            if (id.equals(entity.getId()))
                return entity;
        }
        logger.log(Logger.Level.WARNING, "Couldn't find entity by id: " + id);
        throw new DaoException("Couldn't find entity by id: " + id);
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
        repository.remove(getById(id));
    }
}
