package com.hotel.start;


import com.hotel.facade.HotelFacade;
import com.hotel.ui.menu.MenuController;
import com.hotel.util.SerializationHandler;
import com.hotel.util.IdGenerator;

public class Starter {

    public static void main(String[] args) {

        HotelFacade facade = HotelFacade.getInstance();

//        facade.addRoom(11, 2, 5, 100F);
//        facade.addRoom(22, 1, 4, 90F);
//        facade.addRoom(33, 2, 3, 80F);
//        facade.addRoom(44, 3, 2, 60F);
//        facade.addRoom(55, 2, 1, 40F);
//
//        facade.addGuest("Фамилия1","Имя1");
//        facade.addGuest("Фамилия2","Имя2");
//        facade.addGuest("Фамилия3","Имя3");
//        facade.addGuest("Фамилия4","Имя4");
//        facade.addGuest("Фамилия5","Имя5");
//
//        facade.addService("Стирка", 10F);
//        facade.addService("Глажка", 5F);
//        facade.addService("Обед в номер", 5F);
//        facade.addService("Массаж", 30F);
//
//
//        facade.checkIn(1, 2,  3);
//
//        facade.addServiceToGuest(3, 1);
//
//        facade.checkOut(1);
//        facade.checkIn(2, 2, 3);
//        facade.checkOut(2);
//        facade.checkIn(3, 2, 3);
//        facade.checkOut(3);
//        facade.checkIn(4, 2, 3);
//        facade.checkOut(4);


        MenuController.getInstance().run();

        SerializationHandler.serialize(facade.getAllGuest(), facade.getAllHistories(), facade.getAllRoom(), facade.getAllService());
        SerializationHandler.serialize(IdGenerator.getInstance());

    }
}
