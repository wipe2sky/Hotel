package com.kurtsevich.hotel.server.dao;


import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.DBConnection;

public class MainTest {

    public static void main(String[] args) {
        DBConnection connection = new DBConnection();
        GuestDao guestDaoDB = new GuestDao(connection);
        RoomDao roomDaoDB = new RoomDao(connection);
        ServiceDao serviceDaoDB = new ServiceDao(connection);
        HistoryDao historyDaoDB = new HistoryDao(connection, roomDaoDB, guestDaoDB, serviceDaoDB);

        Guest guest = guestDaoDB.getById(1);
        Guest guest2 = guestDaoDB.getById(2);
        Room room = roomDaoDB.getById(1);
        Room room2 = roomDaoDB.getById(2);
        Service service = serviceDaoDB.getById(1);
        Service service2 = serviceDaoDB.getById(2);

//        HistoryDB history = new HistoryDB(LocalDate.now(), LocalDate.now().plusDays(4), room.getPrice() * 4, room, guest);
//        historyDaoDB.save(history);
//        HistoryDB history2 = new HistoryDB(LocalDate.now(), LocalDate.now().plusDays(5), room2.getPrice() * 5, room2, guest2);
//        historyDaoDB.save(history);
//        historyDaoDB.save(history2);

//        History history = historyDaoDB.getById(1);
//        history.getServices().add(service2);
//        history.setCostOfLiving(history.getCostOfLiving() + service2.getPrice());
//        history.setCostOfService(service2.getPrice());
//        historyDaoDB.update(history);
//        historyDaoDB.delete(historyDaoDB.getById(1));
//        historyDaoDB.delete(historyDaoDB.getById(2));
        guestDaoDB.getAll().forEach(System.out::println);

        connection.close();

    }

}
