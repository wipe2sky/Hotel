package com.kurtsevich.hotel.dao;

import com.kurtsevich.hotel.api.dao.IRoleDao;
import com.kurtsevich.hotel.model.security.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends AbstractDao<Role> implements IRoleDao {
    @Override
    protected Class<Role> getClazz() {
        return Role.class;
    }
}
