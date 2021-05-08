package com.kurtsevich.hotel.api.dao;

import com.kurtsevich.hotel.model.security.User;

public interface IUserDao extends GenericDao<User>{
    User findByUsername(String username);
}
