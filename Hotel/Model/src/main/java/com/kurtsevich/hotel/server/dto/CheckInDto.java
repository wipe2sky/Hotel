package com.kurtsevich.hotel.server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckInDto {
    @NotNull
    private Integer guestId;
    @NotNull
    private Integer roomId;
    @NotNull
    private Integer daysStay;

}
