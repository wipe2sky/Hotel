package com.kurtsevich.hotel.util;

import com.kurtsevich.hotel.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.dto.GuestDto;
import com.kurtsevich.hotel.model.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuestMapper {
    GuestMapper INSTANCE = Mappers.getMapper(GuestMapper.class);

    GuestDto guestToGuestDto(Guest guest);

    Guest guestDtoToGuest(GuestDto guestDTO);

    GuestWithoutHistoriesDto guestToGuestWithoutHistoriesDto(Guest guest);

    Guest guestWithoutHistoriesDtoToGuest(GuestWithoutHistoriesDto guestWithoutHistoriesDto);
}
