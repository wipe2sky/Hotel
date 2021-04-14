package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import lombok.extern.log4j.Log4j2;
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
            throw new DaoException(e);
        }
    }
}
