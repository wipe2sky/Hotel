package com.kurtsevich.hotel.service;

import com.kurtsevich.hotel.SortStatus;
import com.kurtsevich.hotel.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.api.dao.IGuestDao;
import com.kurtsevich.hotel.dto.GuestDto;
import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.util.GuestMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {
    private static Guest testGuest;
    private static GuestDto testGuestDto;
    private static GuestWithoutHistoriesDto testGuestWithoutHistoriesDto;
    private static List<Guest> testGuestsList;
    private static List<GuestDto> testGuestDtoList;
    private static List<GuestWithoutHistoriesDto> testGuestsWithoutHistoriesDtoList;
    @InjectMocks
    private GuestService guestService;
    @Mock
    private IGuestDao guestDao;
    @Mock
    private GuestMapper mapper;

    @BeforeAll
    public static void prepareTestData() {
        testGuest = (Guest) new Guest().setFirstName("TestName").setLastName("TestLastName").setCheckIn(true).setHistories(null).setId(3);
        Guest testGuest2 = (Guest) new Guest().setFirstName("TestName2").setLastName("TestLastName2").setCheckIn(true).setHistories(null).setId(4);

        testGuestDto = new GuestDto().setFirstName("TestName").setLastName("TestLastName").setCheckIn(true).setHistories(null).setId(3);
        GuestDto testGuestDto2 = new GuestDto().setFirstName("TestName2").setLastName("TestLastName2").setCheckIn(true).setId(4);

        testGuestWithoutHistoriesDto = new GuestWithoutHistoriesDto().setFirstName("TestName").setLastName("TestLastName").setCheckIn(true).setId(3);
        GuestWithoutHistoriesDto testGuestWithoutHistoriesDto2 = new GuestWithoutHistoriesDto().setFirstName("TestName2")
                .setLastName("TestLastName2").setCheckIn(true).setId(4);

        testGuestsList = Arrays.asList(testGuest, testGuest2);
        testGuestDtoList = Arrays.asList(testGuestDto, testGuestDto2);
        testGuestsWithoutHistoriesDtoList = Arrays.asList(testGuestWithoutHistoriesDto, testGuestWithoutHistoriesDto2);
    }


    @Test
    void add() {
        when(mapper.guestDtoToGuest(testGuestDto)).thenReturn(testGuest);
        doNothing().when(guestDao).save(testGuest);
        guestService.add(testGuestDto);
        verify(guestDao, times(1)).save(testGuest);
    }

    @Test
    void getById() {
        when(guestDao.getById(anyInt())).thenReturn(testGuest);
        when(mapper.guestToGuestWithoutHistoriesDto(testGuest)).thenReturn(testGuestWithoutHistoriesDto);
        assertEquals(testGuestWithoutHistoriesDto, guestService.getById(2));
    }

    @Test
    void deleteGuest() {
        doNothing().when(guestDao).delete(testGuest);
        when(guestDao.getById(anyInt())).thenReturn(testGuest);
        guestService.deleteGuest(1);
        verify(guestDao, times(1)).delete(testGuest);
    }

    @Test
    void getSortBy() {
        when(guestDao.getSortBy(any(SortStatus.class))).thenReturn(testGuestsList);
        when(mapper.guestToGuestWithoutHistoriesDto(any(Guest.class)))
                .thenReturn(testGuestsWithoutHistoriesDtoList.get(0), testGuestsWithoutHistoriesDtoList.get(1));
        assertEquals(testGuestsWithoutHistoriesDtoList, guestService.getSortBy(SortStatus.DATE_CHECK_OUT));
    }

    @Test
    void getCountGuestInHotel() {
        when(guestDao.getCountGuestInHotel()).thenReturn(3L);
        assertEquals(3, guestService.getCountGuestInHotel());

    }

    @Test
    void getAll() {
        when(guestDao.getAll()).thenReturn(testGuestsList);
        when(mapper.guestToGuestWithoutHistoriesDto(any(Guest.class)))
                .thenReturn(testGuestsWithoutHistoriesDtoList.get(0), testGuestsWithoutHistoriesDtoList.get(1));
        assertEquals(testGuestsWithoutHistoriesDtoList, guestService.getAll());
    }

    @Test
    void getAllGuestInHotel() {
        when(guestDao.getAllGuestInHotel()).thenReturn(testGuestsList);
        when(mapper.guestToGuestDto(any(Guest.class)))
                .thenReturn(testGuestDtoList.get(0), testGuestDtoList.get(1));
        assertEquals(testGuestDtoList, guestService.getAllGuestInHotel());
    }
}