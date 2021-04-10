package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.server.api.dao.GenericDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.AEntity;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDao<T extends AEntity> implements GenericDao<T> {
    protected final Logger logger = LoggerFactory.getLogger(AbstractDao.class);
    protected HibernateConnector connector;
    protected EntityManager em;

    protected abstract Class<T> getClazz();

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public T getById(Integer id) throws DaoException {
        try {
           return em.find(getClazz(), id);

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> getAll() throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(getClazz());
            Root<T> root = cq.from(getClazz());

            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(T entity) throws DaoException{
        try {
            em.remove(entity);
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void update(T entity) throws DaoException{
        try {
            em.merge(entity);
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

}
