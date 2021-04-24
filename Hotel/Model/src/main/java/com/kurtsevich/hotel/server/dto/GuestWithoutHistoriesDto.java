package com.kurtsevich.hotel.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class GuestWithoutHistoriesDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private boolean isCheckIn;
}
