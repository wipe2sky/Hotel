package com.hotel.ui.actions.history;

import com.hotel.exceptions.ServiceException;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetLast3GuestInRoom extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetLast3GuestInRoom.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id номера:");
            Integer roomId = Integer.parseInt(reader.readLine());

            facade.getLast3GuestInRoom(roomId).forEach(System.out::println);
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Get last 3 guest in room history failed", e);
        }
    }
}
