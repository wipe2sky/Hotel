package com.kurtsevich.hotel.dao;

import com.kurtsevich.hotel.api.dao.IServiceDao;
import com.kurtsevich.hotel.api.exceptions.DaoException;
import com.kurtsevich.hotel.model.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ServiceDao extends AbstractDao<Service> implements IServiceDao {

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
            throw new DaoException(e);
        }
    }
}
