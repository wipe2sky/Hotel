package com.kurtsevich.hotel.dto;

import com.kurtsevich.hotel.model.security.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoleWithoutUsersDto {
    private String name;
    private LocalDate created;
    private LocalDate updated;
    private Status activeStatus;
}
