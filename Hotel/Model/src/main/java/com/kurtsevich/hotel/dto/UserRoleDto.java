package com.kurtsevich.hotel.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRoleDto {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer roleId;
}
