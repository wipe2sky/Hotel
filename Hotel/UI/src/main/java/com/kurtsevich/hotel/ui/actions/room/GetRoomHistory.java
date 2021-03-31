package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetRoomHistory extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(GetRoomHistory.class);

    public GetRoomHistory(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());

            facade.getRoomHistory(roomId)
                    .forEach(System.out::println);

            logger.info("History of room № " + roomId + ".");

        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.warn("Get room history failed", e);
        }
    }
}
