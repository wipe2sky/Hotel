package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.RoomDto;
import com.kurtsevich.hotel.server.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.HistoryMapper;
import com.kurtsevich.hotel.server.util.RoomMapper;
import com.kurtsevich.hotel.server.SortStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    @Value("${roomService.allowRoomStatus}")
    private boolean allowRoomStatus;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;

    @Override
    public void addRoom(RoomDto roomDTO) {
        try {
            Room room = RoomMapper.INSTANCE.roomDtoToRoom(roomDTO);
            roomDao.save(room);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Add room failed.");
        }
    }

    @Override
    public void deleteRoom(Integer id) {
        try {
            roomDao.delete(roomDao.getById(id));

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete room failed.");
        }
    }

    @Override
    public void setCleaningStatus(RoomDto roomDTO) {
        Room room;
        try {
            if (!allowRoomStatus) {
                throw new ServiceException("Changed status disable.");
            }
            room = roomDao.getById(roomDTO.getId());

            if (roomDTO.getIsCleaning().equals(room.getIsCleaning())) {
                log.warn("Impossible change status {} on {}", room.getIsCleaning(), room.getIsCleaning());
                throw new ServiceException("Impossible change status.");
            }
            room.setIsCleaning(roomDTO.getIsCleaning());
            roomDao.update(room);

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Set cleaning status failed.");
        }
    }

    @Override
    public void changePrice(RoomDto roomDTO) {
        try {
            Room room = roomDao.getById(roomDTO.getId());
            room.setPrice(roomDTO.getPrice());
            roomDao.update(room);

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Change room price failed.");
        }
    }

    @Override
    public List<RoomWithoutHistoriesDto> getSortBy(SortStatus sortStatus, RoomStatus roomStatus) {
        try {
            List<Room> rooms = roomDao.getSortBy(sortStatus, roomStatus);
            List<RoomWithoutHistoriesDto> roomsDTO = new ArrayList<>();
            rooms.forEach(room -> roomsDTO.add(RoomMapper.INSTANCE.roomToRoomWithoutHistoriesDto(room)));
            return roomsDTO;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sorting room failed.");
        }
    }

    @Override
    @Transactional
    public List<RoomWithoutHistoriesDto> getAvailableAfterDate(LocalDateTime date) {
        try {
            List<Room> rooms = roomDao.getAvailableAfterDate(date);
            List<RoomWithoutHistoriesDto> roomsDTO = new ArrayList<>();
            rooms.forEach(room -> roomsDTO.add(RoomMapper.INSTANCE.roomToRoomWithoutHistoriesDto(room)));
            return roomsDTO;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    public Integer getNumberOfFree() {
        try {
            return roomDao.getNumberOfFree();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get count of free rooms failed.");
        }
    }


    @Override
    public List<HistoryDto> getRoomHistory(Integer roomId) {
        try {
            List<History> histories = historyDao.getRoomHistories(roomDao.getById(roomId));
            List<HistoryDto> historiesDTO = new ArrayList<>();
            histories.forEach(history -> historiesDTO.add(HistoryMapper.INSTANCE.historyToHistoryDto(history)));
            return historiesDTO;

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get room History failed.");
        }
    }

    @Override
    public void setRepairStatus(RoomDto roomDTO) {
        try {
            Room room = roomDao.getById(roomDTO.getId());

            if (!allowRoomStatus) {
                throw new ServiceException("Changed status disable.");
            }
if(room.getStatus().equals(RoomStatus.BUSY)){
    log.warn("Set repair status failed. Room {} is busy}", room.getNumber());
    throw new ServiceException("Set repair status failed. Room is busy");
}
            if (roomDTO.getStatus().equals(room.getStatus())) {
                log.warn("Impossible change status {} on {}", room.getStatus(), room.getStatus());
                throw new ServiceException("Set repair status failed.");
            } else {
                room.setStatus(roomDTO.getStatus());
            }
            roomDao.update(room);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Set repair status failed.");
        }
    }

    @Override
    public List<RoomWithoutHistoriesDto> getAll() {
        try {
            List<Room> rooms = roomDao.getAll();
            List<RoomWithoutHistoriesDto> roomsDTO = new ArrayList<>();
            rooms.forEach(room -> roomsDTO.add(RoomMapper.INSTANCE.roomToRoomWithoutHistoriesDto(room)));
            return roomsDTO;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    public RoomWithoutHistoriesDto getById(Integer roomId) {
        try {
            return RoomMapper.INSTANCE.roomToRoomWithoutHistoriesDto( roomDao.getById(roomId));
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get room by id failed.");
        }
    }
}
