package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RoomDao extends AbstractDao<Room> implements IRoomDao {

    private static final String STATUS = "status";

    @Override
    protected Class<Room> getClazz() {
        return Room.class;
    }

    @Override
    public List<Room> getSortBy(SortStatus sortStatus, RoomStatus roomStatus) throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Room> cq = cb.createQuery(Room.class);
            Root<Room> root = cq.from(Room.class);

            if (roomStatus == RoomStatus.FREE) {
                cq.select(root)
                        .where(cb.equal(root.get(STATUS), RoomStatus.FREE))
                        .orderBy(cb.asc(root.get(sortStatus.getValue())));
            } else {
                cq.select(root)
                        .orderBy(cb.asc(root.get(sortStatus.getValue())));
            }
            TypedQuery<Room> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer getNumberOfFree() throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Room> cq = cb.createQuery(Room.class);
            Root<Room> root = cq.from(Room.class);
            Predicate statusPredicate = cb.equal(root.get(STATUS), RoomStatus.FREE);
            cq.select(root).where(statusPredicate);
            Query query = em.createQuery(cq);
            return query.getResultList().size();
        } catch (Exception e) {
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
            Predicate roomStatusFreePredicate = cb.equal(root.get(STATUS), RoomStatus.FREE);
            Predicate roomCheckOutDatePredicate = cb.lessThan(roomHistoryJoin.get("checkOutDate"), date);
            Predicate finalPredicate = cb.or(roomStatusFreePredicate, roomCheckOutDatePredicate);
            cq.select(root)
                    .where(finalPredicate);
            TypedQuery<Room> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
