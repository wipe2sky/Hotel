package com.kurtsevich.hotel.dto;

import com.kurtsevich.hotel.SortStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SortStatusDto {
    @NotNull
    private SortStatus sortStatus;
}
