package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Singleton
public class GuestDao extends AbstractDao<Guest> implements IGuestDao {
    private final Logger logger = LoggerFactory.getLogger(GuestDao.class);


    @InjectByType
    public GuestDao(HibernateConnector connector) {
        this.connector = connector;
        this.em = connector.getEntityManager();
    }


    @Override
    protected Class<Guest> getClazz() {
        return Guest.class;
    }

    @Override
    public List<Guest> getSortBy(SortStatus sortStatus) throws DaoException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
            Root<History> root = cq.from(History.class);
            Order order = null;
            if (sortStatus.equals(SortStatus.DATE_CHECK_OUT)) {
                order = cb.asc(root.get(sortStatus.getValue()));
            } else if (sortStatus.equals(SortStatus.LAST_NAME)) {
                order = cb.asc(root.get("guest").get(sortStatus.getValue()));
            }
            cq.select(root.get("guest"))
                    .where(cb.equal(root.get("guest").get("isCheckIn"), true))
                    .orderBy(order);
            TypedQuery<Guest> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Long getCountGuestInHotel() throws DaoException {

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
            Root<Guest> root = cq.from(Guest.class);
            Predicate isCheckInPredicate = cb.equal(root.get("isCheckIn"), true);
            cq.select(root).where(isCheckInPredicate);
            Query query = em.createQuery(cq);
            return Long.valueOf(query.getResultList().size());
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Guest> getAllGuestInHotel() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
            Root<Guest> root = cq.from(Guest.class);
            Predicate isCheckInPredicate = cb.equal(root.get("isCheckIn"), true);
            cq.select(root).where(isCheckInPredicate);
            Query query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }



    @Override
    public List<Guest> getLast3GuestInRoom(Room room) throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
            Root<History> root = cq.from(History.class);
            cq.select(root.get("guest"))
                    .where(cb.equal(root.get("room"), room.getId()));
            TypedQuery<Guest> query = em.createQuery(cq);
            return query.setMaxResults(3).getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }
}


