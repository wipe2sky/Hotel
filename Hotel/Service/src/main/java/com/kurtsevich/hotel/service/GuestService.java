package com.kurtsevich.hotel.service;

import com.kurtsevich.hotel.SortStatus;
import com.kurtsevich.hotel.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.api.dao.IGuestDao;
import com.kurtsevich.hotel.api.service.IGuestService;
import com.kurtsevich.hotel.dto.GuestDto;
import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.util.GuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService implements IGuestService {
    private final IGuestDao guestDao;
    private final GuestMapper mapper;

    @Override
    public void add(GuestDto guestDTO) {
        Guest guest = mapper.guestDtoToGuest(guestDTO);
        guestDao.save(guest);
    }


    @Override
    public GuestWithoutHistoriesDto getById(Integer id) {
        return mapper.guestToGuestWithoutHistoriesDto(guestDao.getById(id));
    }

    @Override
    public void deleteGuest(Integer id) {
        guestDao.delete(guestDao.getById(id));
    }

    @Override
    public List<GuestWithoutHistoriesDto> getSortBy(SortStatus sortStatus) {
        return guestDao.getSortBy(sortStatus).stream()
                .map(mapper::guestToGuestWithoutHistoriesDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long getCountGuestInHotel() {
        return guestDao.getCountGuestInHotel();
    }

    @Override
    public List<GuestWithoutHistoriesDto> getAll() {
        return guestDao.getAll().stream()
                .map(mapper::guestToGuestWithoutHistoriesDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GuestDto> getAllGuestInHotel() {
        return guestDao.getAllGuestInHotel().stream()
                .map(mapper::guestToGuestDto)
                .collect(Collectors.toList());
    }
}
