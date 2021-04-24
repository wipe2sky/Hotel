package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.dto.GuestDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.GuestMapper;
import com.kurtsevich.hotel.server.SortStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class GuestService implements IGuestService {
    private final IGuestDao guestDao;

    @Override
    public void add(GuestDto guestDTO) {
        try {
            Guest guest = GuestMapper.INSTANCE.guestDtoToGuest(guestDTO);
            guestDao.save(guest);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Couldn't add Guest");
        }
    }


    @Override
    public GuestWithoutHistoriesDto getById(Integer id) {
        try {
            return GuestMapper.INSTANCE.guestToGuestWithoutHistoriesDto(guestDao.getById(id));
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage());
            throw new ServiceException("Couldn't find entity by id: " + id);
        }
    }

    @Override
    public void deleteGuest(Integer id) {
        try {
            guestDao.delete(guestDao.getById(id));
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete guest failed.");
        }
    }

    @Override
    public List<GuestWithoutHistoriesDto> getSortBy(SortStatus sortStatus) {
        try {
            List<Guest> guests = guestDao.getSortBy(sortStatus);
            List<GuestWithoutHistoriesDto> guestsDTO = new ArrayList<>();
            guests.forEach(guest -> guestsDTO.add(GuestMapper.INSTANCE.guestToGuestWithoutHistoriesDto(guest)));
            return guestsDTO;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sort guest failed.");
        }

    }

    @Override
    public Long getCountGuestInHotel() {
        try {
            return guestDao.getCountGuestInHotel();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get count of guest failed.");
        }
    }

    @Override
    public List<GuestWithoutHistoriesDto> getAll() {
        try {
            List<Guest> guests = guestDao.getAll();
            List<GuestWithoutHistoriesDto> guestsDTO = new ArrayList<>();
            guests.forEach(g -> guestsDTO.add(GuestMapper.INSTANCE.guestToGuestWithoutHistoriesDto(g)));
            return guestsDTO;

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get guests failed.");
        }
    }


    @Override
    public List<GuestDto> getAllGuestInHotel() {
        try {
            List<Guest> guests = guestDao.getAllGuestInHotel();
            List<GuestDto> guestsDTO = new ArrayList<>();
            guests.forEach(g -> guestsDTO.add(GuestMapper.INSTANCE.guestToGuestDto(g)));
            return guestsDTO;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get all guests in hotel failed.");
        }
    }
}
