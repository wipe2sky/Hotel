package com.kurtsevich.hotel.util;

import com.kurtsevich.hotel.dto.UserDto;
import com.kurtsevich.hotel.model.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
