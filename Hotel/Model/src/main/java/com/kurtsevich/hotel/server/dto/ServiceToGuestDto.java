package com.kurtsevich.hotel.server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceToGuestDto {
    @NotNull
    private Integer serviceId;
    @NotNull
    private Integer guestId;

}
