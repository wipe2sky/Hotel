package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddRoom extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(AddRoom.class);

    public AddRoom(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите номер комнаты");
            Integer number = Integer.parseInt(reader.readLine());
            System.out.println("Введите вместимость");
            Integer capacity = Integer.parseInt(reader.readLine());
            System.out.println("Введите звёздность комнаты");
            Integer stars = Integer.parseInt(reader.readLine());
            System.out.println("Введите стоимость");
            Double price = Double.parseDouble(reader.readLine());

            Room room = facade.addRoom(number, capacity, stars, price);

            logger.info("Room added " + room);
        } catch (NumberFormatException |IOException e) {
            logger.warn("Added room failed", e);
        }
    }
}
