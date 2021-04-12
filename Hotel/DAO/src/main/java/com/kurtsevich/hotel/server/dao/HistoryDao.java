package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Singleton
public class HistoryDao extends AbstractDao<History> implements IHistoryDao {
    private final Logger logger = LoggerFactory.getLogger(HistoryDao.class);
    @ConfigProperty
    private Integer countOfHistories;

    @InjectByType
    public HistoryDao(HibernateConnector connector) {
        this.connector = connector;
        this.em = connector.getEntityManager();
    }


    @Override
    protected Class<History> getClazz() {
        return History.class;
    }

    @Override
    public List<History> getGuestHistories(Guest guest) throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<History> cq = cb.createQuery(History.class);
            Root<History> root = cq.from(History.class);
            cq.select(root)
                    .where(cb.equal(root.get("guest"), guest.getId()))
                    .orderBy(cb.desc(root.get("checkOutDate")));
            TypedQuery<History> query = em.createQuery(cq);
            return query.setMaxResults(countOfHistories).getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<History> getRoomHistories(Room room) throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<History> cq = cb.createQuery(History.class);
            Root<History> root = cq.from(History.class);

            cq.select(root)
                    .where(cb.equal(root.get("room"), room.getId()))
                    .orderBy(cb.desc(root.get("checkOutDate")));
            TypedQuery<History> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }



}
