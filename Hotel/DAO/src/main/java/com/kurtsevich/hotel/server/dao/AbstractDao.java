package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.server.api.dao.GenericDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.NotFoundEntityException;
import com.kurtsevich.hotel.server.model.AEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDao<T extends AEntity> implements GenericDao<T> {
    @PersistenceContext
    protected EntityManager em;

    protected abstract Class<T> getClazz();

    @Override
    public void save(T entity) {
        try {
            em.persist(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T getById(Integer id) throws DaoException {
        T entity = em.find(getClazz(), id);
        if (entity == null) {
            throw new NotFoundEntityException(id);
        } else {
            return entity;
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(getClazz());
            Root<T> root = cq.from(getClazz());

            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(T entity) throws DaoException {
        try {
            em.remove(entity);
        } catch (Exception e) {
            throw new DaoException("Delete guest failed.", e);
        }
    }

    @Override
    public void update(T entity) throws DaoException {
        try {
            em.merge(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
