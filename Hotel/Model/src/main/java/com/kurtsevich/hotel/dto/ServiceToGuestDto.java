package com.kurtsevich.hotel.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceToGuestDto {
    @NotNull
    private Integer serviceId;
    @NotNull
    private Integer guestId;

}
