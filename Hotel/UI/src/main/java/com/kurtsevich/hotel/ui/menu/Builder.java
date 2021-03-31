package com.kurtsevich.hotel.ui.menu;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;
import com.kurtsevich.hotel.ui.actions.ActionsFactory;
import com.kurtsevich.hotel.ui.actions.guest.*;
import com.kurtsevich.hotel.ui.actions.history.*;
import com.kurtsevich.hotel.ui.actions.room.*;
import com.kurtsevich.hotel.ui.actions.service.*;

@Singleton
public class Builder {
    private Menu rootMenu;
    private static final String EXIT = "Выход";
    private static final String COMEBACK = "Назад";
    private final ActionsFactory factory;

    @InjectByType
    public Builder( ActionsFactory factory) {
        this.factory = factory;
    }

    public void buildMenu() {
        rootMenu = new Menu("Главное меню");

        rootMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, rootMenu));
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
        }, rootMenu));
        roomMenu.addMenuItems(new MenuItem("Поставить номер на уборку", factory.getAction(SetRoomCleaningStatus.class, true), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Снять номер с уборки", factory.getAction(SetRoomCleaningStatus.class, false), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Информация о номере", factory.getAction(GetRoomInfo.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem("История номера", factory.getAction(GetRoomHistory.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Количество всех свободных комнат", factory.getAction(GetNumberOfFree.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Получить список всех свободных комнат на дату", factory.getAction(GetRoomAvailableAfterDate.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Получить список всех комнат", factory.getAction(GetAllRoom.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Сортировать номера по", () -> {
        }, createRoomSortMenu()));
        roomMenu.addMenuItems(new MenuItem("Изменить цену номера", factory.getAction(ChangeRoomPrice.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Поставить номер в ремонт", factory.getAction(SetRoomRepairStatus.class, true), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Снять номер с ремонта", factory.getAction(SetRoomRepairStatus.class, false), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Добавить номер", factory.getAction(AddRoom.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem("Удалить номер", factory.getAction(DeleteRoom.class), roomMenu));
        roomMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return roomMenu;
    }

    private Menu createRoomSortMenu() {
        Menu roomSortMenu = new Menu("Сортировать номера по:");

        roomSortMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, rootMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по цене", factory.getAction(GetSortRoomBy.class, ComparatorStatus.PRICE, RoomStatus.FREE), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по вместимости", factory.getAction(GetSortRoomBy.class, ComparatorStatus.CAPACITY, RoomStatus.FREE), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Сободные номера по звёздности", factory.getAction(GetSortRoomBy.class, ComparatorStatus.STARS, RoomStatus.FREE), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Все номера по цене", factory.getAction(GetSortRoomBy.class, ComparatorStatus.PRICE, null), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Все номера по вместимости", factory.getAction(GetSortRoomBy.class, ComparatorStatus.CAPACITY, null), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem("Все номера по звёздности", factory.getAction(GetSortRoomBy.class, ComparatorStatus.STARS, null), roomSortMenu));
        roomSortMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return roomSortMenu;
    }

    private Menu createGuestMenu() {
        Menu guestMenu = new Menu("Меню управления гостями");

        guestMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, rootMenu));
        guestMenu.addMenuItems(new MenuItem("Добавить гостя", factory.getAction(AddGuest.class), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Удалить гостя", factory.getAction(DeleteGuest.class), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить список гостей за всё время ", factory.getAction(GetAllGuest.class), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить всех постояльцев в отеле", factory.getAction(GetAllGuestInHotel.class), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Получить количество постояльцев в отеле", factory.getAction(GetCountGuestInHotel.class), guestMenu));
        guestMenu.addMenuItems(new MenuItem("Сортировать постояльцев", () -> {
        }, createGuestSortMenu()));
        guestMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return guestMenu;
    }

    private Menu createGuestSortMenu() {
        Menu guestSortMenu = new Menu("Сортировать гостей по:");

        guestSortMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, rootMenu));
        guestSortMenu.addMenuItems(new MenuItem("Сортировать по фамилии", factory.getAction(GetGuestSortBy.class, ComparatorStatus.LAST_NAME), guestSortMenu));
        guestSortMenu.addMenuItems(new MenuItem("Сортировать по дате выезда", factory.getAction(GetGuestSortBy.class, ComparatorStatus.DATE_CHECK_OUT), guestSortMenu));
        guestSortMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));
        return guestSortMenu;
    }

    private Menu createServiceMenu() {
        Menu serviceMenu = new Menu("Меню управления услугами");

        serviceMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, rootMenu));
        serviceMenu.addMenuItems(new MenuItem("Добавить гостю услугу", factory.getAction(AddServiceToGuest.class), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Получить список услуг", factory.getAction(GetAllService.class), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Сортировать услуги по стоимости", factory.getAction(GetServiceSortByPrice.class), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Добавить услугу", factory.getAction(AddService.class), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Изменить стоимость услуги", factory.getAction(ChangeServicePrice.class), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem("Удалить услугу", factory.getAction(DeleteService.class), serviceMenu));
        serviceMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return serviceMenu;
    }

    private Menu createHistoryMenu() {
        Menu historyMenu = new Menu("Меню управления бронированием");

        historyMenu.addMenuItems(new MenuItem(EXIT, () -> {
        }, rootMenu));
        historyMenu.addMenuItems(new MenuItem("Check-In", factory.getAction(CheckIn.class), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Check-Out", factory.getAction(CheckOut.class), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать список услуг гостя", factory.getAction(GetListOfGuestService.class), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Получить историю гостя", factory.getAction(GetGuestHistory.class), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать стомость проживания гостя", factory.getAction(GetCostOfLiving.class), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Показать 3-х последних гостей номера", factory.getAction(GetLast3GuestInRoom.class), historyMenu));
        historyMenu.addMenuItems(new MenuItem("Получить историю за всё время", factory.getAction(GetAllHistories.class), historyMenu));
        historyMenu.addMenuItems(new MenuItem(COMEBACK, () -> {
        }, rootMenu));

        return historyMenu;
    }
}
