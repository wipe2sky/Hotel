package com.kurtsevich.hotel.server.dto;

import com.kurtsevich.hotel.server.SortStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SortStatusDto {
    @NotNull
    private SortStatus sortStatus;
}
