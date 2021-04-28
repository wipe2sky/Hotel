package com.kurtsevich.hotel.server.api.service;

import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.RoomDto;
import com.kurtsevich.hotel.server.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.SortStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {

    void addRoom(RoomDto roomDTO);

    RoomWithoutHistoriesDto getById(Integer roomId);

    List<RoomWithoutHistoriesDto> getAll();

    void deleteRoom(Integer id);

    void setCleaningStatus(RoomDto roomDTO);

    void changePrice(RoomDto roomDTO);

    List<RoomWithoutHistoriesDto> getSortBy(SortStatus sortStatus, RoomStatus roomStatus);

    List<RoomWithoutHistoriesDto> getAvailableAfterDate(LocalDateTime date);


    Integer getNumberOfFree();

    List<HistoryDto> getRoomHistory(Integer roomId);

    void setRepairStatus(RoomDto roomDTO);


}

