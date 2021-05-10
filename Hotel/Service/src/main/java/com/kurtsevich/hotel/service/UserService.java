package com.kurtsevich.hotel.service;

import com.kurtsevich.hotel.api.dao.IRoleDao;
import com.kurtsevich.hotel.api.dao.IUserDao;
import com.kurtsevich.hotel.api.service.IUserService;
import com.kurtsevich.hotel.dto.UserDto;
import com.kurtsevich.hotel.dto.UserRoleDto;
import com.kurtsevich.hotel.model.security.Role;
import com.kurtsevich.hotel.model.security.User;
import com.kurtsevich.hotel.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final IRoleDao roleDao;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDao.save(user);
        log.info("In register - user {} successfully registered", user);
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Integer id) {
        return mapper.userToUserDto(userDao.getById(id));
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(userDao.getById(id));
    }

    @Override
    public void addUserRole(UserRoleDto userRoleDto) {
        User user = userDao.getById(userRoleDto.getUserId());
        Role role = roleDao.getById(userRoleDto.getRoleId());

        user.getRoles().add(role);
        user.setUpdated(new Date());
        userDao.update(user);
    }

    @Override
    public void deleteUserRole(UserRoleDto userRoleDto) {
        User user = userDao.getById(userRoleDto.getUserId());
        Role role = roleDao.getById(userRoleDto.getRoleId());

        user.getRoles().remove(role);
        user.setUpdated(new Date());
        userDao.update(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
