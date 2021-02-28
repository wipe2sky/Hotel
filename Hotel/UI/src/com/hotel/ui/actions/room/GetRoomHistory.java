package com.hotel.ui.actions.room;

import com.hotel.exceptions.ServiceException;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetRoomHistory extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetRoomHistory.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());

            logger.log(Logger.Level.INFO, "History of room № " + roomId + ".");

            facade.GetRoomHistory(roomId).stream()
                    .forEach(System.out::println);

        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.log(Logger.Level.WARNING, "Get room history failed", e);
        }
    }
}
