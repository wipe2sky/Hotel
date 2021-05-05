package com.kurtsevich.hotel.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
@Data
public class DateDto {
    @NotNull
    private LocalDate date;
}
