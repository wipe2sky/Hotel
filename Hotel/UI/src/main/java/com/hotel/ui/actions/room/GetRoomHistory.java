package com.hotel.ui.actions.room;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class GetRoomHistory  implements IAction {
    private static final Logger logger = new Logger(GetRoomHistory.class.getName());
    @InjectByType
    private HotelFacade facade;

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());

            facade.getRoomHistory(roomId).stream()
                    .forEach(System.out::println);

            logger.log(Logger.Level.INFO, "History of room № " + roomId + ".");

        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.log(Logger.Level.WARNING, "Get room history failed", e);
        }
    }
}
