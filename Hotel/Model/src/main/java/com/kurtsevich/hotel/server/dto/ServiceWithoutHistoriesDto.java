package com.kurtsevich.hotel.server.dto;

import lombok.Data;


@Data
public class ServiceWithoutHistoriesDto {
    private Integer id;
    private String name;
    private Double price;
}
