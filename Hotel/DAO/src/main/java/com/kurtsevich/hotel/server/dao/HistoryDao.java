package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class HistoryDao extends AbstractDao<History> implements IHistoryDao {
    private final Logger logger = LoggerFactory.getLogger(HistoryDao.class);

    @InjectByType
    public HistoryDao(HibernateConnector connector) {
        this.connector = connector;
        this.em = connector.getEntityManager();
    }


    @Override
    protected Class<History> getClazz() {
        return History.class;
    }

    public List<History> getByGuest(Guest entity) throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<History> cq = cb.createQuery(History.class);
            Root<History> root = cq.from(History.class);
            cq.select(root)
                    .where(cb.equal(root.get("guest"), entity.getId()))
                    .orderBy(cb.desc(root.get("checkOutDate")));
            TypedQuery<History> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Room> getAvailableAfterDate(LocalDateTime date) throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Room> cq = cb.createQuery(Room.class);
            Root<Room> root = cq.from(Room.class);
            Join<Room, History> roomHistoryJoin = root.join("histories", JoinType.LEFT);
            Predicate roomStatusFreePredicate = cb.equal(root.get("status"), RoomStatus.FREE);
            Predicate roomCheckOutDatePredicate = cb.lessThan(roomHistoryJoin.get("checkOutDate"), date);
            Predicate finalPredicate = cb.or(roomStatusFreePredicate, roomCheckOutDatePredicate);
            cq.select(root)
                    .where(finalPredicate);
            TypedQuery<Room> query = em.createQuery(cq);
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
