package com.kurtsevich.hotel.dao;

import com.kurtsevich.hotel.api.dao.IHistoryDao;
import com.kurtsevich.hotel.api.exceptions.DaoException;
import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Room;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class HistoryDao extends AbstractDao<History> implements IHistoryDao {

    @Value("${historyDao.countOfHistories}")
    private Integer countOfHistories;


    @Override
    protected Class<History> getClazz() {
        return History.class;
    }

    @Override
    public List<History> getGuestHistories(Guest guest) throws DaoException {
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
            throw new DaoException(e);
        }
    }

    @Override
    public History getCurrentGuestHistories(Guest guest) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<History> cq = cb.createQuery(History.class);
            Root<History> root = cq.from(History.class);
            cq.select(root)
                    .where(cb.and(cb.equal(root.get("guest"), guest.getId()), cb.equal(root.get("isCurrent"), true)))
                    .orderBy(cb.desc(root.get("checkOutDate")));
            TypedQuery<History> query = em.createQuery(cq);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Guest don't lives in hotel");
        }
    }

    @Override
    public List<History> getRoomHistories(Room room) throws DaoException {
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
            throw new DaoException(e);
        }
    }
}
