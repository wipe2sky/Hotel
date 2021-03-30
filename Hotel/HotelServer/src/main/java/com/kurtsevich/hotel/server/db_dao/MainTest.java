package com.kurtsevich.hotel.server.db_dao;


import com.kurtsevich.hotel.server.db_model.GuestDB;
import com.kurtsevich.hotel.server.db_model.HistoryDB;
import com.kurtsevich.hotel.server.db_model.RoomDB;
import com.kurtsevich.hotel.server.db_model.ServiceDB;

import java.time.LocalDate;

public class MainTest {

    public static void main(String[] args) {
        DBConnection connection = new DBConnection();
        GuestDaoDB guestDaoDB = new GuestDaoDB(connection);
        RoomDaoDB roomDaoDB = new RoomDaoDB(connection);
        ServiceDaoDB serviceDaoDB = new ServiceDaoDB(connection);
        HistoryDaoDB historyDaoDB = new HistoryDaoDB(connection, roomDaoDB, guestDaoDB, serviceDaoDB);

        GuestDB guest = guestDaoDB.getById(1);
        GuestDB guest2 = guestDaoDB.getById(2);
        RoomDB room = roomDaoDB.getById(1);
        RoomDB room2 = roomDaoDB.getById(2);
        ServiceDB service = serviceDaoDB.getById(1);
        ServiceDB service2 = serviceDaoDB.getById(2);

//        HistoryDB history = new HistoryDB(LocalDate.now(), LocalDate.now().plusDays(4), room.getPrice() * 4, room, guest);
//        historyDaoDB.save(history);
//        HistoryDB history2 = new HistoryDB(LocalDate.now(), LocalDate.now().plusDays(5), room2.getPrice() * 5, room2, guest2);
//        historyDaoDB.save(history);
//        historyDaoDB.save(history2);

        HistoryDB history = historyDaoDB.getById(1);
//        history.getServices().add(service2);
//        history.setCostOfLiving(history.getCostOfLiving() + service2.getPrice());
//        history.setCostOfService(service2.getPrice());
//        historyDaoDB.update(history);
        historyDaoDB.delete(historyDaoDB.getById(1));
//        historyDaoDB.delete(historyDaoDB.getById(2));
        historyDaoDB.getAll().forEach(System.out::println);

        connection.close();

    }

}
