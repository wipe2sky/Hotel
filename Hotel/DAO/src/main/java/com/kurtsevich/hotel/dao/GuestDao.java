package com.kurtsevich.hotel.dao;

import com.kurtsevich.hotel.SortStatus;
import com.kurtsevich.hotel.api.dao.IGuestDao;
import com.kurtsevich.hotel.api.exceptions.DaoException;
import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class GuestDao extends AbstractDao<Guest> implements IGuestDao {

    private static final String IS_CHECK_IN = "isCheckIn";
    private static final String GUEST = "guest";

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
                order = cb.asc(root.get(GUEST).get(sortStatus.getValue()));
            }
            cq.select(root.get(GUEST))
                    .where(cb.equal(root.get(GUEST).get(IS_CHECK_IN), true))
                    .orderBy(order)
                    .groupBy(root.get(GUEST));
            TypedQuery<Guest> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long getCountGuestInHotel() throws DaoException {

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
            Root<Guest> root = cq.from(Guest.class);
            Predicate isCheckInPredicate = cb.equal(root.get(IS_CHECK_IN), true);
            cq.select(root).where(isCheckInPredicate);
            Query query = em.createQuery(cq);
            return (long) query.getResultList().size();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Guest> getAllGuestInHotel() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
            Root<Guest> root = cq.from(Guest.class);
            Predicate isCheckInPredicate = cb.equal(root.get(IS_CHECK_IN), true);
            cq.select(root).where(isCheckInPredicate);
            TypedQuery<Guest> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }


    @Override
    public List<Guest> getLast3GuestInRoom(Room room) throws DaoException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
            Root<History> root = cq.from(History.class);
            cq.select(root.get(GUEST))
                    .where(cb.equal(root.get("room"), room.getId()));
            TypedQuery<Guest> query = em.createQuery(cq);
            return query.setMaxResults(3).getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}


