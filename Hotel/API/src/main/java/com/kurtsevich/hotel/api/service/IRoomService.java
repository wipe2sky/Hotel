package com.kurtsevich.hotel.api.service;

import com.kurtsevich.hotel.dto.HistoryDto;
import com.kurtsevich.hotel.dto.RoomDto;
import com.kurtsevich.hotel.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.model.RoomStatus;
import com.kurtsevich.hotel.SortStatus;

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

