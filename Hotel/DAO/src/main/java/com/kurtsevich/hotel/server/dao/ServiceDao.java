package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Singleton
public class ServiceDao extends AbstractDao<Service> implements IServiceDao {
    private final Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    @InjectByType
    public ServiceDao(HibernateConnector connection) {
        this.connector = connection;
        this.em = connector.getEntityManager();
    }


    @Override
    protected Class<Service> getClazz() {
        return Service.class;
    }

    @Override
    public List<Service> getSortByPrice() throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Service> cq = cb.createQuery(Service.class);
            Root<Service> root = cq.from(Service.class);

            cq.select(root)
                    .orderBy(cb.asc(root.get("price")));
            TypedQuery<Service> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }
}
