package com.hotel.ui.menu;

import com.hotel.model.RoomStatus;
import com.hotel.ui.actions.guest.*;
import com.hotel.ui.actions.history.*;
import com.hotel.ui.actions.room.*;
import com.hotel.ui.actions.service.*;
import com.hotel.util.comparators.ComparatorStatus;

import java.util.Objects;

public class Builder {
    private static Builder instance;
    private Menu rootMenu;

    private Builder() {
    }

    public static Builder getInstance() {
        return Objects.requireNonNullElse(instance, new Builder());
    }
    private static final String EXIT = "Выход";
    private static final String COMEBACK = "Назад";

    public void buildMenu() {
        rootMenu = new Menu("Главное меню");

        rootMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, null));
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

        roomMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, null));
        roomMenu.addMenuItems(new MenuItem("Добавить номер", new AddRoom(), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Поставить номер на уборку", new SetRoomCleaningStatus(true), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Снять номер с уборки", new SetRoomCleaningStatus(false), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Информация о номере", new GetRoomInfo(), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Количество всех свободных комнат", new GetNumberOfFree(), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Получить список всех свободных комнат на дату", new GetRoomAvailableAfterDate(), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Получить список всех комнат", new GetAllRoom(), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Сортировать номера по", () -> {
        }, createRoomSortMenu()));
        roomMenu.addMenuItems(new MenuItem("Изменить цену номера", new ChangeRoomPrice(), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Поставить номер в ремонт", new SetRoomRepairStatus(true), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Снять номер с ремонта", new SetRoomRepairStatus(false), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Удалить номер", new DeleteRoom(), roomMenu));
        roomMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return roomMenu;
    }

    private Menu createRoomSortMenu() {
        Menu roomSortMenu = new Menu("Сортировать номера по:");

        roomSortMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, null));
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

        guestMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, null));
        guestMenu.addMenuItems(new MenuItem("Добавить гостя", new AddGuest(), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Удалить гостя", new DeleteGuest(), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить список гостей за всё время ", new GetAllGuest(), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить всех постояльцев в отеле", new GetAllGuestInHotel(), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить количество постояльцев в отеле", new GetCountGuestInHotel(), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Сортировать постояльцев", () -> {
        }, createGuestSortMenu()));
        guestMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return guestMenu;
    }

    private Menu createGuestSortMenu() {
        Menu guestSortMenu = new Menu("Сортировать гостей по:");

        guestSortMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, null));
        guestSortMenu.addMenuItems(new MenuItem("Сортировать по фамилии", new GetGuestSortBy(ComparatorStatus.LAST_NAME), guestSortMenu));
        guestSortMenu.addMenuItems(new MenuItem("Сортировать по дате выезда", new GetGuestSortBy(ComparatorStatus.DATE_CHECK_OUT), guestSortMenu));
        guestSortMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));
        return guestSortMenu;
    }

    private Menu createServiceMenu() {
        Menu serviceMenu = new Menu("Меню управления услугами");

        serviceMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, null));
        serviceMenu.addMenuItems(new MenuItem("Добавить гостю услугу", new AddServiceToGuest(), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Получить список услуг", new GetAllService(), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Сортировать услуги по стоимости", new GetServiceSortByPrice(), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Добавить услугу", new AddService(), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Изменить стоимость услуги", new ChangeServicePrice(), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Удалить услугу", new DeleteService(), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return serviceMenu;
    }

    //    private Menu createServiceSortMenu(){
//        Menu serviceSortMenu = new Menu("Сортировать услуги по:");
//        serviceSortMenu.addMenuItems(new MenuItem(exit, () -> {
//        }, null));
//
//        serviceSortMenu.addMenuItems(new MenuItem("Назад в главное меню", () -> {
//        }, rootMenu));
//        return serviceSortMenu;
//    }
    private Menu createHistoryMenu() {
        Menu historyMenu = new Menu("Меню управления бронированием");

        historyMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, null));
        historyMenu.addMenuItems(new MenuItem("Check-In", new CheckIn(), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Check-Out", new CheckOut(), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать список услуг гостя", new GetListOfGuestService(), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Получить историю гостя", new GetGuestHistory(), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать стомость проживания гостя", new GetCostOfLiving(), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать 3-х последних гостей номера", new GetLast3GuestInRoom(), historyMenu));
        historyMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return historyMenu;
    }
}
