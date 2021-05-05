package com.kurtsevich.hotel.api.service;

import com.kurtsevich.hotel.dto.GuestDto;
import com.kurtsevich.hotel.SortStatus;
import com.kurtsevich.hotel.dto.GuestWithoutHistoriesDto;

import java.util.List;

public interface IGuestService {
    void add(GuestDto guestDTO);

    GuestWithoutHistoriesDto getById(Integer id);

    void deleteGuest(Integer id);

    List<GuestWithoutHistoriesDto> getSortBy(SortStatus sortStatus);

    Long getCountGuestInHotel();

    List<GuestWithoutHistoriesDto> getAll();


    List<GuestDto> getAllGuestInHotel();

}
