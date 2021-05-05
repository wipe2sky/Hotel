package com.kurtsevich.hotel.service;

import com.kurtsevich.hotel.SortStatus;
import com.kurtsevich.hotel.dto.HistoryDto;
import com.kurtsevich.hotel.api.dao.IHistoryDao;
import com.kurtsevich.hotel.api.dao.IRoomDao;
import com.kurtsevich.hotel.api.exceptions.ServiceException;
import com.kurtsevich.hotel.dto.RoomDto;
import com.kurtsevich.hotel.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Room;
import com.kurtsevich.hotel.model.RoomStatus;
import com.kurtsevich.hotel.util.HistoryMapper;
import com.kurtsevich.hotel.util.RoomMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    private static Room testRoom;
    private static Room testRoom2;
    private static RoomDto testRoomDto;
    private static RoomWithoutHistoriesDto testRoomWithoutHistoriesDto;
    private static RoomWithoutHistoriesDto testRoomWithoutHistoriesDto2;
    private static List<Room> testRooms;
    private static List<RoomWithoutHistoriesDto> testRoomsWithoutHistoriesDto;
    private static List<History> testHistories;
    private static List<HistoryDto> testHistoriesDto;
    @InjectMocks
    private RoomService roomService;
    @Mock
    private IRoomDao roomDao;
    @Mock
    private IHistoryDao historyDao;
    @Mock
    private RoomMapper roomMapper;
    @Mock
    private HistoryMapper historyMapper;

    @BeforeAll
    public static void prepareTestData() {
        History testHistory = (History) new History().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(3))
                .setCostOfService(0D).setServices(null).setCostOfLiving(300D).setCurrent(true).setGuest(null)
                .setRoom(testRoom).setId(1);
        History testHistory2 = (History) new History().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(5))
                .setCostOfService(0D).setServices(null).setCostOfLiving(300D).setCurrent(true).setGuest(null)
                .setRoom(testRoom2).setId(1);

        HistoryDto testHistoryDto = new HistoryDto().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(3))
                .setCostOfService(0D).setServices(null).setCostOfLiving(750D).setCurrent(true).setGuest(null)
                .setRoom(testRoomWithoutHistoriesDto).setId(1);
        HistoryDto testHistoryDto2 = new HistoryDto().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(5))
                .setCostOfService(0D).setServices(null).setCostOfLiving(300D).setCurrent(true).setGuest(null)
                .setRoom(testRoomWithoutHistoriesDto2).setId(1);

        testRoom = (Room) new Room().setNumber(11).setPrice(100D).setGuestsInRoom(1).setCapacity(1)
                .setIsCleaning(false).setHistories(Collections.singletonList(testHistory)).setStars(5).setId(1);
        testRoom2 = (Room) new Room().setNumber(22).setPrice(150D).setGuestsInRoom(1).setCapacity(2)
                .setIsCleaning(false).setHistories(Collections.singletonList(testHistory2)).setStars(5).setId(2);

        testRoomDto = new RoomDto().setNumber(11).setPrice(100D).setGuestsInRoom(1).setCapacity(1)
                .setIsCleaning(false).setHistories(Collections.singletonList(testHistoryDto)).setStars(5).setId(1);

        testRoomWithoutHistoriesDto = new RoomWithoutHistoriesDto().setNumber(11).setPrice(100D).setGuestsInRoom(1).setCapacity(1)
                .setIsCleaning(false).setStars(5).setId(1);
        testRoomWithoutHistoriesDto2 = new RoomWithoutHistoriesDto().setNumber(22).setPrice(150D).setGuestsInRoom(1).setCapacity(2)
                .setIsCleaning(false).setStars(5).setId(2);

        testRooms = Arrays.asList(testRoom, testRoom2);
        testRoomsWithoutHistoriesDto = Arrays.asList(testRoomWithoutHistoriesDto, testRoomWithoutHistoriesDto2);

        testHistories = Arrays.asList(testHistory, testHistory2);
        testHistoriesDto = Arrays.asList(testHistoryDto, testHistoryDto2);
    }

    @Test
    void addRoom() {
        when(roomMapper.roomDtoToRoom(testRoomDto)).thenReturn(testRoom);
        doNothing().when(roomDao).save(testRoom);
        roomService.addRoom(testRoomDto);
        verify(roomDao, times(1)).save(testRoom);
    }

    @Test
    void deleteRoom() {
        doNothing().when(roomDao).delete(testRoom);
        when(roomDao.getById(anyInt())).thenReturn(testRoom);
        roomService.deleteRoom(2);
        verify(roomDao, times(1)).delete(testRoom);
    }

    @Test
    @SneakyThrows
    void setCleaningStatus() {
        doNothing().when(roomDao).update(testRoom);
        when(roomDao.getById(anyInt())).thenReturn(testRoom);

        Field field = roomService.getClass().getDeclaredField("allowRoomStatus");
        field.setAccessible(true);
        field.set(roomService, true);

        testRoomDto.setIsCleaning(true);
        roomService.setCleaningStatus(testRoomDto);

        verify(roomDao, times(1)).getById(anyInt());
        verify(roomDao, times(1)).update(testRoom);
        assertEquals(testRoom.getIsCleaning(), testRoomDto.getIsCleaning());

    }

    @Test
    void setCleaningStatus_ServiceException() {
        assertThrows(ServiceException.class, () -> roomService.setCleaningStatus(testRoomDto));
    }

    @Test
    void changePrice() {
        doNothing().when(roomDao).update(testRoom);
        when(roomDao.getById(anyInt())).thenReturn(testRoom);

        roomService.changePrice(testRoomDto);

        verify(roomDao, times(1)).getById(anyInt());
        verify(roomDao, times(1)).update(testRoom);
    }

    @Test
    void getSortBy() {
        when(roomDao.getSortBy(any(SortStatus.class), any(RoomStatus.class))).thenReturn(testRooms);
        when(roomMapper.roomToRoomWithoutHistoriesDto(any(Room.class)))
                .thenReturn(testRoomsWithoutHistoriesDto.get(0), testRoomsWithoutHistoriesDto.get(1));

        assertEquals(testRoomsWithoutHistoriesDto, roomService.getSortBy(SortStatus.STARS, RoomStatus.FREE));

    }

    @Test
    void getAvailableAfterDate() {
        when(roomDao.getAvailableAfterDate(any(LocalDateTime.class))).thenReturn(testRooms);
        when(roomMapper.roomToRoomWithoutHistoriesDto(any(Room.class)))
                .thenReturn(testRoomsWithoutHistoriesDto.get(0), testRoomsWithoutHistoriesDto.get(1));

        assertEquals(testRoomsWithoutHistoriesDto, roomService.getAvailableAfterDate(LocalDateTime.now()));
    }

    @Test
    void getNumberOfFree() {
        when(roomDao.getNumberOfFree()).thenReturn(2);

        assertEquals(2, roomService.getNumberOfFree());
    }

    @Test
    void getRoomHistory() {
        when(roomDao.getById(anyInt())).thenReturn(testRoom);
        when(historyDao.getRoomHistories(testRoom)).thenReturn(testHistories);
        when(historyMapper.historyToHistoryDto(any(History.class))).thenReturn(testHistoriesDto.get(0), testHistoriesDto.get(1));

        assertEquals(testHistoriesDto, roomService.getRoomHistory(2));
    }

    @Test
    @SneakyThrows
    void setRepairStatus() {
        doNothing().when(roomDao).update(testRoom);
        when(roomDao.getById(anyInt())).thenReturn(testRoom);

        Field field = roomService.getClass().getDeclaredField("allowRoomStatus");
        field.setAccessible(true);
        field.set(roomService, true);

        testRoomDto.setStatus(RoomStatus.REPAIR);
        roomService.setRepairStatus(testRoomDto);

        verify(roomDao, times(1)).getById(anyInt());
        verify(roomDao, times(1)).update(testRoom);
        assertEquals(testRoom.getStatus(), testRoomDto.getStatus());
    }

    @Test
    void setRepairStatus_ServiceException() {
        assertThrows(ServiceException.class, () -> roomService.setRepairStatus(testRoomDto));
    }

    @Test
    void getAll() {
        when(roomDao.getAll()).thenReturn(testRooms);
        when(roomMapper.roomToRoomWithoutHistoriesDto(any(Room.class)))
                .thenReturn(testRoomsWithoutHistoriesDto.get(0), testRoomsWithoutHistoriesDto.get(1));

        assertEquals(testRoomsWithoutHistoriesDto, roomService.getAll());
    }

    @Test
    void getById() {
        when(roomDao.getById(anyInt())).thenReturn(testRoom);
        when(roomMapper.roomToRoomWithoutHistoriesDto(testRoom)).thenReturn(testRoomWithoutHistoriesDto);

        assertEquals(testRoomWithoutHistoriesDto, roomService.getById(2));
    }
}