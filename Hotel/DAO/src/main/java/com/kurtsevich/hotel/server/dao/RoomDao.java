package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Singleton
public class RoomDao extends AbstractDao<Room> implements IRoomDao {
    private final Logger logger = LoggerFactory.getLogger(RoomDao.class);

    @InjectByType
    public RoomDao(HibernateConnector connection) {
        this.connector = connection;
        this.em = connector.getEntityManager();
    }



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
                        .where(cb.equal(root.get("status"), RoomStatus.FREE))
                        .orderBy(cb.asc(root.get(sortStatus.getValue())));
            } else {
                cq.select(root)
                        .orderBy(cb.asc(root.get(sortStatus.getValue())));
            }
            TypedQuery<Room> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Integer getNumberOfFree() throws DaoException{
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Room> cq = cb.createQuery(Room.class);
            Root<Room> root = cq.from(Room.class);
            Predicate statusPredicate = cb.equal(root.get("status"), RoomStatus.FREE);
            cq.select(root).where(statusPredicate);
            Query query = em.createQuery(cq);
            return query.getResultList().size();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<History> getHistory(Room room) throws DaoException{
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
