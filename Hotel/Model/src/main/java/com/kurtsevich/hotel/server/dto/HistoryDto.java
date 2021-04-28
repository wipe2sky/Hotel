package com.kurtsevich.hotel.server.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HistoryDto {
    private Integer id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Double costOfLiving;
    private Double costOfService;
    private boolean isCurrent;
    private RoomWithoutHistoriesDto room;
    private GuestWithoutHistoriesDto guest;
    private List<ServiceWithoutHistoriesDto> services;
}
