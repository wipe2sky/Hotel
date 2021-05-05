package com.kurtsevich.hotel.service;

import com.kurtsevich.hotel.api.dao.IGuestDao;
import com.kurtsevich.hotel.api.dao.IHistoryDao;
import com.kurtsevich.hotel.api.dao.IServiceDao;
import com.kurtsevich.hotel.dto.HistoryDto;
import com.kurtsevich.hotel.dto.ServiceDto;
import com.kurtsevich.hotel.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.dto.ServiceWithoutHistoriesDto;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Service;
import com.kurtsevich.hotel.util.ServiceMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceForServiceTest {
    private static Service testService;
    private static ServiceDto testServiceDto;
    private static ServiceWithoutHistoriesDto testServiceWithoutHistoriesDto;
    private static ServiceWithoutHistoriesDto testServiceWithoutHistoriesDto2;
    private static ServiceToGuestDto testServiceToGuestDto;
    private static History testHistory;
    private static Guest testGuest;
    private static List<Service> testServices;
    private static List<ServiceWithoutHistoriesDto> testServicesWithoutHistoriesDto;
    private static List<History> testHistoriesList;
    @InjectMocks
    ServiceForService service;
    @Mock
    private IServiceDao serviceDao;
    @Mock
    private IGuestDao guestDao;
    @Mock
    private IHistoryDao historyDao;
    @Mock
    private ServiceMapper mapper;

    @BeforeAll
    public static void prepareTestData() {
        testGuest = (Guest) new Guest().setFirstName("TestName").setLastName("TestLastName").setCheckIn(true).setHistories(testHistoriesList).setId(3);

        testHistory = (History) new History().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(3))
                .setCostOfService(0D).setServices(new ArrayList<>()).setCostOfLiving(300D).setCurrent(true).setGuest(testGuest)
                .setRoom(null).setId(1);
        History testHistory2 = (History) new History().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(5))
                .setCostOfService(0D).setServices(null).setCostOfLiving(300D).setCurrent(true).setGuest(null)
                .setRoom(null).setId(1);
        HistoryDto testHistoryDto = new HistoryDto().setCheckInDate(LocalDateTime.now()).setCheckOutDate(LocalDateTime.now().plusDays(3))
                .setCostOfService(0D).setServices(null).setCostOfLiving(750D).setCurrent(true).setGuest(null)
                .setRoom(null).setId(1);

        testHistoriesList = new ArrayList<>(Collections.singletonList(testHistory));
        List<History> testHistoriesList2 = new ArrayList<>(Collections.singletonList(testHistory2));
        List<HistoryDto> testHistoriesDtoList = new ArrayList<>(Collections.singletonList(testHistoryDto));

        testServiceToGuestDto = new ServiceToGuestDto().setServiceId(1).setGuestId(1);


        testService = (Service) new Service().setName("Test1").setPrice(10D).setHistories(testHistoriesList).setId(1);
        Service testService2 = (Service) new Service().setName("Test2").setPrice(20D).setHistories(testHistoriesList2).setId(2);
        testServices = Arrays.asList(testService, testService2);

        testServiceDto = new ServiceDto().setName("Test1").setPrice(10D).setHistories(testHistoriesDtoList).setId(1);

        testServiceWithoutHistoriesDto = new ServiceWithoutHistoriesDto().setName("Test1").setPrice(10D).setId(1);
        testServiceWithoutHistoriesDto2 = new ServiceWithoutHistoriesDto().setName("Test2").setPrice(20D).setId(2);
        testServicesWithoutHistoriesDto = Arrays.asList(testServiceWithoutHistoriesDto, testServiceWithoutHistoriesDto2);

    }

    @Test
    void addService() {
        doNothing().when(serviceDao).save(testService);
        when(mapper.serviceDtoToService(testServiceDto)).thenReturn(testService);

        service.addService(testServiceDto);
        verify(serviceDao, times(1)).save(testService);
    }

    @Test
    void deleteService() {
        when(serviceDao.getById(anyInt())).thenReturn(testService);
        doNothing().when(serviceDao).delete(testService);

        service.deleteService(1);
        verify(serviceDao, times(1)).delete(testService);
    }

    @Test
    void getById() {
        when(serviceDao.getById(anyInt())).thenReturn(testService);
        when(mapper.serviceToServiceWithoutHistoriesDTO(testService)).thenReturn(testServiceWithoutHistoriesDto);
        assertEquals(testServiceWithoutHistoriesDto, service.getById(1));
    }

    @Test
    void addServiceToGuest() {
        when(serviceDao.getById(anyInt())).thenReturn(testService);
        when(guestDao.getById(anyInt())).thenReturn(testGuest);
        when(historyDao.getCurrentGuestHistories(testGuest)).thenReturn(testHistory);
        doNothing().when(historyDao).update(testHistory);

        service.addServiceToGuest(testServiceToGuestDto);

        verify(historyDao, times(1)).update(testHistory);


    }

    @Test
    void getSortByPrice() {
        when(serviceDao.getSortByPrice()).thenReturn(testServices);
        when(mapper.serviceToServiceWithoutHistoriesDTO(any()))
                .thenReturn(testServiceWithoutHistoriesDto, testServiceWithoutHistoriesDto2);

        assertEquals(testServicesWithoutHistoriesDto, service.getSortByPrice());
    }

    @Test
    void getAll() {
        when(serviceDao.getAll()).thenReturn(testServices);
        when(mapper.serviceToServiceWithoutHistoriesDTO(any(Service.class)))
                .thenReturn(testServiceWithoutHistoriesDto, testServiceWithoutHistoriesDto2);
        assertEquals(testServicesWithoutHistoriesDto, service.getAll());
    }

    @Test
    void changeServicePrice() {
        when(serviceDao.getById(anyInt())).thenReturn(testService);
        doNothing().when(serviceDao).update(testService);

        service.changeServicePrice(testServiceDto);

        verify(serviceDao, times(1)).update(testService);
    }
}