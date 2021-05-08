package com.kurtsevich.hotel.api.service;

import com.kurtsevich.hotel.dto.UserRoleDto;
import com.kurtsevich.hotel.dto.UserDto;
import com.kurtsevich.hotel.model.security.User;

import java.util.List;

public interface IUserService {
    void register(UserDto userDto);

    List<UserDto> getAll();

    UserDto getById(Integer id);

    void delete(Integer id);

    void addUserRole(UserRoleDto userRoleDto);
    void deleteUserRole(UserRoleDto userRoleDto);

    User findByUsername(String username);
}
