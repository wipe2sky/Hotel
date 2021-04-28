package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.RoomDto;
import com.kurtsevich.hotel.server.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.HistoryMapper;
import com.kurtsevich.hotel.server.util.RoomMapper;
import com.kurtsevich.hotel.server.SortStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    @Value("${roomService.allowRoomStatus}")
    private boolean allowRoomStatus;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;
    private final RoomMapper roomMapper;
    private final HistoryMapper historyMapper;

    @Override
    public void addRoom(RoomDto roomDTO) {
            Room room = roomMapper.roomDtoToRoom(roomDTO);
            roomDao.save(room);
    }

    @Override
    public void deleteRoom(Integer id) {
            roomDao.delete(roomDao.getById(id));
    }

    @Override
    public void setCleaningStatus(RoomDto roomDTO) {
        Room room;
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
    }

    @Override
    public void changePrice(RoomDto roomDTO) {
            Room room = roomDao.getById(roomDTO.getId());
            room.setPrice(roomDTO.getPrice());
            roomDao.update(room);
    }

    @Override
    public List<RoomWithoutHistoriesDto> getSortBy(SortStatus sortStatus, RoomStatus roomStatus) {
            return roomDao.getSortBy(sortStatus, roomStatus).stream()
                    .map(roomMapper::roomToRoomWithoutHistoriesDto)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RoomWithoutHistoriesDto> getAvailableAfterDate(LocalDateTime date) {
            return roomDao.getAvailableAfterDate(date).stream()
                    .map(roomMapper::roomToRoomWithoutHistoriesDto)
                    .collect(Collectors.toList());
    }

    @Override
    public Integer getNumberOfFree() {
            return roomDao.getNumberOfFree();
    }


    @Override
    public List<HistoryDto> getRoomHistory(Integer roomId) {
            return historyDao.getRoomHistories(roomDao.getById(roomId)).stream()
                    .map(historyMapper::historyToHistoryDto)
                    .collect(Collectors.toList());
    }

    @Override
    public void setRepairStatus(RoomDto roomDTO) {
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
    }

    @Override
    public List<RoomWithoutHistoriesDto> getAll() {
            return roomDao.getAll().stream()
                    .map(roomMapper::roomToRoomWithoutHistoriesDto)
                    .collect(Collectors.toList());
    }

    @Override
    public RoomWithoutHistoriesDto getById(Integer roomId) {
            return roomMapper.roomToRoomWithoutHistoriesDto( roomDao.getById(roomId));
    }
}
