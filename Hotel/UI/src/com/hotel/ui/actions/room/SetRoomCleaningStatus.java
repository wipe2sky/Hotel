package com.hotel.ui.actions.room;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Room;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomCleaningStatus extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(SetRoomCleaningStatus.class.getName());

    private boolean isCleaning;

    public SetRoomCleaningStatus(boolean isCleaning) {
        this.isCleaning = isCleaning;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());
            Room room = facade.getRoomInfo(roomId);

            facade.setRoomCleaningStatus(roomId, isCleaning);

            if (isCleaning) {
                logger.log(Logger.Level.INFO, "Room " + room.getNumber() + " is cleaned");
            } else {
                logger.log(Logger.Level.INFO, "Room " + room.getNumber() + " cleaned");

            }

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, e.getMessage(), e);
        }
    }
}
