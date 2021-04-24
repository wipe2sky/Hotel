package com.kurtsevich.hotel.server.dto;

import lombok.Data;


@Data
public class ServiceWithoutHistoriesDTO {
    private Integer id;
    private String name;
    private Double price;
}
