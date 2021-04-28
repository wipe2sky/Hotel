package com.kurtsevich.hotel.server.dto;

import com.kurtsevich.hotel.server.model.RoomStatus;
import lombok.Data;

import java.util.List;

@Data
public class RoomDto {
    private Integer id;
    private Integer number;
    private Integer capacity;
    private Integer stars;
    private Double price;
    private RoomStatus status = RoomStatus.FREE;
    private Integer guestsInRoom = 0;
    private Boolean isCleaning = false;
    private List<HistoryDto> histories;
}
