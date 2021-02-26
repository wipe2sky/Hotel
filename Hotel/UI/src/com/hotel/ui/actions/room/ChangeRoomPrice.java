package com.hotel.ui.actions.room;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Room;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeRoomPrice extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(ChangeRoomPrice.class.getName());

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
