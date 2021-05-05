package com.kurtsevich.hotel.service;

import com.kurtsevich.hotel.api.dao.IGuestDao;
import com.kurtsevich.hotel.api.dao.IHistoryDao;
import com.kurtsevich.hotel.api.dao.IRoomDao;
import com.kurtsevich.hotel.dto.*;
import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Room;
import com.kurtsevich.hotel.model.Service;
import com.kurtsevich.hotel.util.GuestMapper;
import com.kurtsevich.hotel.util.HistoryMapper;
import com.kurtsevich.hotel.util.ServiceMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {
    private static Guest testGuest;
    private static GuestWithoutHistoriesDto testGuestWithoutHistoriesDto;
    private static Room testRoom;
    private static RoomWithoutHistoriesDto testRoomWithoutHistoriesDto;
    private static Service testService;
    private static ServiceWithoutHistoriesDto testServiceWithoutHistoriesDto;
    private static History testHistory;
    private static HistoryDto testHistoryDto;
    private static CheckInDto testCheckInDto;
    private static List<History> testHistoriesList;
    private static List<HistoryDto> testHistoriesDtoList;
    private static List<Guest> testGuestsList;
    private static List<GuestWithoutHistoriesDto> testGuestsWithoutHistoriesDtoList;
    private static List<ServiceWithoutHistoriesDto> testServiceWithoutHistoriesDtoList;
    @InjectMocks
    HistoryService historyService;
    @Mock
    private IGuestDao guestDao;
    @Mock
    private IRoomDao roomDao;
    @Mock
    private IHistoryDao historyDao;
    @Mock
    private HistoryMapper historyMapper;
    @Mock
    private GuestMapper guestMapper;
    @Mock
    private ServiceMapper serviceMapper;

    @BeforeAll
    public static void prepareTestData() {
        testGuest = (Guest) new Guest().setFirstName("TestName").setLastName("TestLastName").setCheckIn(true).setHistories(testHistoriesList).setId(3);
        testGuestWithoutHistoriesDto = new GuestWithoutHistoriesDto().setFirstName("TestName").setLastName("TestLastName").setCheckIn(true).setId(3);
        testRoom = (Room) new Room().setNumber(11).setPrice(100D).setGuestsInRoom(1).setCapacity(2)
                .setIsCleaning(false).setHistories(Collections.singletonList(testHistory)).setStars(5).setId(1);
        testRoomWithoutHistoriesDto = new RoomWithoutHistoriesDto().setNumber(11).setPrice(100D).setGuestsInRoom(1).setCapacity(2)
                .setIsCleaning(false).setStars(5).setId(1);
        testService = (Service) new Service().setName("Test1").setPrice(10D).setHistories(testHistoriesList).setId(1);
        testServiceWithoutHistoriesDto = new ServiceWithoutHistoriesDto().setName("Test1").setPrice(10D).setId(1);
        testHistory = (History) new History().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(3))
                .setCostOfService(0D).setServices(new ArrayList<>(Collections.singletonList(testService))).setCostOfLiving(300D).setCurrent(true).setGuest(testGuest)
                .setRoom(testRoom).setId(1);
        testHistoryDto = new HistoryDto().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(3))
                .setCostOfService(0D).setServices(new ArrayList<>()).setCostOfLiving(300D).setCurrent(true).setGuest(testGuestWithoutHistoriesDto)
                .setRoom(testRoomWithoutHistoriesDto).setId(1);
        testCheckInDto = new CheckInDto().setRoomId(testGuest.getId()).setGuestId(testRoom.getId()).setDaysStay(3);
        testHistoriesList = new ArrayList<>(Collections.singletonList(testHistory));
        testHistoriesDtoList = new ArrayList<>(Collections.singletonList(testHistoryDto));
        testGuestsList = new ArrayList<>(Collections.singletonList(testGuest));
        testGuestsWithoutHistoriesDtoList = new ArrayList<>(Collections.singletonList(testGuestWithoutHistoriesDto));
        testServiceWithoutHistoriesDtoList = new ArrayList<>(Collections.singletonList(testServiceWithoutHistoriesDto));


    }


    @Test
    void addHistory() {
        doNothing().when(historyDao).save(any(History.class));
        History actualHistory = historyService.addHistory(testRoom, testGuest, 3);


        assertEquals(testHistory.getGuest(), actualHistory.getGuest());
        assertEquals(testHistory.getRoom(), actualHistory.getRoom());
        assertEquals(testHistory.getCostOfLiving(), actualHistory.getCostOfLiving());
        verify(historyDao, times(1)).save(any(History.class));
    }

    @Test
    void checkIn() {
        when(roomDao.getById(anyInt())).thenReturn(testRoom);
        when(guestDao.getById(anyInt())).thenReturn(testGuest);

        doNothing().when(roomDao).update(testRoom);
        doNothing().when(guestDao).update(testGuest);

        historyService.checkIn(testCheckInDto);

        verify(roomDao, times(1)).getById(anyInt());
        verify(guestDao, times(1)).getById(anyInt());
        verify(roomDao, times(1)).update(testRoom);
        verify(guestDao, times(1)).update(testGuest);
    }

    @Test
    void checkOut() {
        when(guestDao.getById(anyInt())).thenReturn(testGuest);
        when(historyDao.getCurrentGuestHistories(testGuest)).thenReturn(testHistory);
        doNothing().when(roomDao).update(testRoom);
        doNothing().when(guestDao).update(testGuest);
        doNothing().when(historyDao).update(testHistory);

        historyService.checkOut(3);

        verify(guestDao, times(1)).getById(anyInt());
        verify(historyDao, times(1)).getCurrentGuestHistories(testGuest);
        verify(roomDao, times(1)).update(testRoom);
        verify(guestDao, times(1)).update(testGuest);
        verify(historyDao, times(1)).update(testHistory);
    }

    @Test
    void getCostOfLiving() {
        when(guestDao.getById(anyInt())).thenReturn(testGuest);
        when(historyDao.getCurrentGuestHistories(testGuest)).thenReturn(testHistory);
        assertEquals(testHistory.getCostOfLiving(), historyService.getCostOfLiving(testGuest.getId()));
    }

    @Test
    void getLast3GuestInRoom() {
        when(roomDao.getById(anyInt())).thenReturn(testRoom);
        when(guestDao.getLast3GuestInRoom(testRoom)).thenReturn(testGuestsList);
        when(guestMapper.guestToGuestWithoutHistoriesDto(testGuest)).thenReturn(testGuestWithoutHistoriesDto);
        assertEquals(testGuestsWithoutHistoriesDtoList, historyService.getLast3GuestInRoom(testRoom.getId()));
    }

    @Test
    void getGuestHistory() {
        when(guestDao.getById(anyInt())).thenReturn(testGuest);
        when(historyDao.getGuestHistories(testGuest)).thenReturn(testHistoriesList);
        when(historyMapper.historyToHistoryDto(testHistory)).thenReturn(testHistoryDto);
        assertEquals(testHistoriesDtoList, historyService.getGuestHistory(testGuest.getId()));
    }

    @Test
    void getAll() {
        when(historyDao.getAll()).thenReturn(testHistoriesList);
        assertEquals(testHistoriesList, historyDao.getAll());
    }

    @Test
    void getListOfGuestService() {
        when(guestDao.getById(anyInt())).thenReturn(testGuest);
        when(serviceMapper.serviceToServiceWithoutHistoriesDTO(testService)).thenReturn(testServiceWithoutHistoriesDto);
        when(historyDao.getCurrentGuestHistories(testGuest)).thenReturn(testHistory);
        assertEquals(testServiceWithoutHistoriesDtoList, historyService.getListOfGuestService(testGuest.getId()));
    }
}