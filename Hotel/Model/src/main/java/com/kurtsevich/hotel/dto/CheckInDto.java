package com.kurtsevich.hotel.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Data
@Validated
public class CheckInDto {
    @NotNull
    private Integer guestId;
    @NotNull
    private Integer roomId;
    @NotNull
    private Integer daysStay;

}
