package com.kurtsevich.hotel.server.dto;

import lombok.Data;

import java.util.List;
@Data
public class ServiceDto {
    private Integer id;
    private String name;
    private Double price;
    private List<HistoryDto> histories;
}
