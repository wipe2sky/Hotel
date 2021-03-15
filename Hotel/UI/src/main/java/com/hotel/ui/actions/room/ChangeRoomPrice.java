package com.hotel.ui.actions.room;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.Room;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class ChangeRoomPrice  implements IAction {
    private static final Logger logger = new Logger(ChangeRoomPrice.class.getName());
    @InjectByType
    private HotelFacade facade;

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());
            System.out.println("Введите новую цену");
            Float price = Float.parseFloat(reader.readLine());

            facade.changeRoomPrice(roomId, price);
            Room room = facade.getRoomInfo(roomId);
            logger.log(Logger.Level.INFO, "Cost of living per day in the room " + room.getNumber() + " has been changed to " + room.getPrice());

        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.log(Logger.Level.WARNING, "Change room price failed", e);
        }
    }
}
