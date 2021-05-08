package com.kurtsevich.hotel.dto;

import com.kurtsevich.hotel.model.security.Role;
import com.kurtsevich.hotel.model.security.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date created;
    private Date updated;
    private Status activeStatus;

    private List<RoleWithoutUsersDto> roles;
}
