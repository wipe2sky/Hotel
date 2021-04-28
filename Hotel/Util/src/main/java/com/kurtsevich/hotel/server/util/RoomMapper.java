package com.kurtsevich.hotel.server.util;

import com.kurtsevich.hotel.server.dto.RoomDto;
import com.kurtsevich.hotel.server.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.Room;
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
