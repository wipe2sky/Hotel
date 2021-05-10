package com.kurtsevich.hotel.dao;

import com.kurtsevich.hotel.api.dao.IUserDao;
import com.kurtsevich.hotel.api.exceptions.DaoException;
import com.kurtsevich.hotel.api.exceptions.NotFoundEntityException;
import com.kurtsevich.hotel.model.security.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDao extends AbstractDao<User> implements IUserDao {
    @Override
    protected Class<User> getClazz() {
        return User.class;
    }

    @Override
    public User findByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root)
                .where(cb.equal(root.get("username"), username));
        TypedQuery<User> query = em.createQuery(cq);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundEntityException(username);
        }
    }
}
