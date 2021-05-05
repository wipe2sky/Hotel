package com.kurtsevich.hotel.util;

import com.kurtsevich.hotel.dto.RoomDto;
import com.kurtsevich.hotel.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDto roomToRoomDto(Room room);

    Room roomDtoToRoom(RoomDto roomDTO);

    RoomWithoutHistoriesDto roomToRoomWithoutHistoriesDto(Room room);

    Room roomWithoutHistoriesDtoToRoom(RoomWithoutHistoriesDto roomWithoutHistoriesDto);

}
