package com.hotel.ui.actions.room;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Room;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetRoomInfo extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetRoomInfo.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());

            Room room = facade.getRoomInfo(roomId);

            logger.log(Logger.Level.INFO, room.toString());

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Get room info failed", e);
        }
    }
}
