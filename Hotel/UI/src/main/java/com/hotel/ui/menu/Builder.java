package com.hotel.ui.menu;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.model.RoomStatus;
import com.hotel.server.util.comparators.ComparatorStatus;
import com.hotel.ui.actions.SaveProgram;
import com.hotel.ui.actions.guest.*;
import com.hotel.ui.actions.history.*;
import com.hotel.ui.actions.room.*;
import com.hotel.ui.actions.service.*;

@Singleton
public class Builder {
//    private static Builder instance;
    private Menu rootMenu;
    private static final String EXIT = "Выход";
    private static final String COMEBACK = "Назад";
    @InjectByType
    private GetRoomInfo getRoomInfo;
    @InjectByType
    private GetRoomHistory getRoomHistory;
    @InjectByType
    private GetNumberOfFree getNumberOfFree;
    @InjectByType
    private GetRoomAvailableAfterDate getRoomAvailableAfterDate;
    @InjectByType
    private GetAllRoom getAllRoom;
    @InjectByType
    private ChangeRoomPrice changeRoomPrice;
    @InjectByType
    private AddRoom addRoom;
    @InjectByType
    private DeleteRoom deleteRoom;
    @InjectByType
    private AddGuest addGuest;
    @InjectByType
    private DeleteGuest deleteGuest;
    @InjectByType
    private GetAllGuest getAllGuest;
    @InjectByType
    private GetAllGuestInHotel getAllGuestInHotel;
    @InjectByType
    private GetCountGuestInHotel getCountGuestInHotel;
    @InjectByType
    private AddServiceToGuest addServiceToGuest;
    @InjectByType
    private GetAllService getAllService;
    @InjectByType
    private GetServiceSortByPrice getServiceSortByPrice;
    @InjectByType
    private AddService addService;
    @InjectByType
    private ChangeServicePrice changeServicePrice;
    @InjectByType
    private DeleteService deleteService;
    @InjectByType
    private CheckIn checkIn;
    @InjectByType
    private CheckOut checkOut;
    @InjectByType
    private GetListOfGuestService getListOfGuestService;
    @InjectByType
    private GetGuestHistory getGuestHistory;
    @InjectByType
    private GetCostOfLiving getCostOfLiving;
    @InjectByType
    private GetLast3GuestInRoom getLast3GuestInRoom;
    @InjectByType
    private GetAllHistories getAllHistories;
    @InjectByType
    private SaveProgram saveProgram;

//    private Builder() {
//    }

//    public static Builder getInstance() {
//        if (instance == null) instance = new Builder();
//        return instance;
//    }


    public void buildMenu() {
        rootMenu = new Menu("Главное меню");

        rootMenu.addMenuItems(new MenuItem(EXIT, saveProgram, rootMenu));
        rootMenu.addMenuItems(new MenuItem("Управление номерами", () -> {
        }, createRoomMenu()));
        rootMenu.addMenuItems(new MenuItem("Управление гостями", () -> {
        }, createGuestMenu()));
        rootMenu.addMenuItems(new MenuItem("Управление услугами", () -> {
        }, createServiceMenu()));
        rootMenu.addMenuItems(new MenuItem("Управление бронированием", () -> {
        }, createHistoryMenu()));
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    private Menu createRoomMenu() {
        Menu roomMenu = new Menu("Меню управления номерами");

        roomMenu.addMenuItems(new MenuItem(EXIT, saveProgram, rootMenu));
        roomMenu.addMenuItems(new MenuItem("Поставить номер на уборку", new SetRoomCleaningStatus(true), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Снять номер с уборки", new SetRoomCleaningStatus(false), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Информация о номере", getRoomInfo, roomMenu));
        roomMenu.addMenuItems(new MenuItem("История номера", getRoomHistory, roomMenu));
        roomMenu.addMenuItems(new MenuItem("Количество всех свободных комнат", getNumberOfFree, roomMenu));
        roomMenu.addMenuItems(new MenuItem("Получить список всех свободных комнат на дату", getRoomAvailableAfterDate, roomMenu));
        roomMenu.addMenuItems(new MenuItem("Получить список всех комнат", getAllRoom, roomMenu));
        roomMenu.addMenuItems(new MenuItem("Сортировать номера по", () -> {
        }, createRoomSortMenu()));
        roomMenu.addMenuItems(new MenuItem("Изменить цену номера", changeRoomPrice, roomMenu));
        roomMenu.addMenuItems(new MenuItem("Поставить номер в ремонт", new SetRoomRepairStatus(true), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Снять номер с ремонта", new SetRoomRepairStatus(false), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Добавить номер", addRoom, roomMenu));
        roomMenu.addMenuItems(new MenuItem("Удалить номер", deleteRoom, roomMenu));
        roomMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return roomMenu;
    }

    private Menu createRoomSortMenu() {
        Menu roomSortMenu = new Menu("Сортировать номера по:");

        roomSortMenu.addMenuItems(new MenuItem(EXIT, saveProgram, rootMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по цене", new GetSortRoomBy(ComparatorStatus.PRICE, RoomStatus.FREE), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по вместимости", new GetSortRoomBy(ComparatorStatus.CAPACITY, RoomStatus.FREE), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по звёздности", new GetSortRoomBy(ComparatorStatus.STARS, RoomStatus.FREE), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по цене", new GetSortRoomBy(ComparatorStatus.PRICE, null), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по вместимости", new GetSortRoomBy(ComparatorStatus.CAPACITY, null), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по звёздности", new GetSortRoomBy(ComparatorStatus.STARS, null), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return roomSortMenu;
    }

    private Menu createGuestMenu() {
        Menu guestMenu = new Menu("Меню управления гостями");

        guestMenu.addMenuItems(new MenuItem(EXIT, saveProgram, rootMenu));
        guestMenu.addMenuItems(new MenuItem("Добавить гостя", addGuest, guestMenu));
        guestMenu.addMenuItems(new MenuItem("Удалить гостя", deleteGuest, guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить список гостей за всё время ", getAllGuest, guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить всех постояльцев в отеле", getAllGuestInHotel, guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить количество постояльцев в отеле", getCountGuestInHotel, guestMenu));
        guestMenu.addMenuItems(new MenuItem("Сортировать постояльцев", () -> {
        }, createGuestSortMenu()));
        guestMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return guestMenu;
    }

    private Menu createGuestSortMenu() {
        Menu guestSortMenu = new Menu("Сортировать гостей по:");

        guestSortMenu.addMenuItems(new MenuItem(EXIT, saveProgram, rootMenu));
        guestSortMenu.addMenuItems(new MenuItem("Сортировать по фамилии", new GetGuestSortBy(ComparatorStatus.LAST_NAME), guestSortMenu));
        guestSortMenu.addMenuItems(new MenuItem("Сортировать по дате выезда", new GetGuestSortBy(ComparatorStatus.DATE_CHECK_OUT), guestSortMenu));
        guestSortMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));
        return guestSortMenu;
    }

    private Menu createServiceMenu() {
        Menu serviceMenu = new Menu("Меню управления услугами");

        serviceMenu.addMenuItems(new MenuItem(EXIT, saveProgram, rootMenu));
        serviceMenu.addMenuItems(new MenuItem("Добавить гостю услугу", addServiceToGuest, serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Получить список услуг", getAllService, serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Сортировать услуги по стоимости", getServiceSortByPrice, serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Добавить услугу", addService, serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Изменить стоимость услуги", changeServicePrice, serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Удалить услугу", deleteService, serviceMenu));
        serviceMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return serviceMenu;
    }

    private Menu createHistoryMenu() {
        Menu historyMenu = new Menu("Меню управления бронированием");

        historyMenu.addMenuItems(new MenuItem(EXIT, saveProgram, rootMenu));
        historyMenu.addMenuItems(new MenuItem("Check-In", checkIn, historyMenu));
        historyMenu.addMenuItems(new MenuItem("Check-Out", checkOut, historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать список услуг гостя", getListOfGuestService, historyMenu));
        historyMenu.addMenuItems(new MenuItem("Получить историю гостя", getGuestHistory, historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать стомость проживания гостя", getCostOfLiving, historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать 3-х последних гостей номера", getLast3GuestInRoom, historyMenu));
        historyMenu.addMenuItems(new MenuItem("Получить историю за всё время", getAllHistories, historyMenu));
        historyMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return historyMenu;
    }
}
