package com.hotel.start;


import com.hotel.factory.Application;
import com.hotel.factory.ApplicationContext;
import com.hotel.factory.corona_disinfector.CoronaDisinfector;
import com.hotel.factory.corona_disinfector.Policeman;
import com.hotel.factory.corona_disinfector.PolicemanImpl;
import com.hotel.factory.corona_disinfector.Room;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.util.IdGenerator;
import com.hotel.ui.menu.MenuController;

import java.util.HashMap;
import java.util.Map;


public class Starter {


    public static void main(String[] args) {


        ApplicationContext context = Application.run("com.hotel", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));

//        HotelFacade facade = context.getObject(HotelFacade.class);
//
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

        MenuController menuController=  context.getObject(MenuController.class);
        menuController.run();

    }
}
