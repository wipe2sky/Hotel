package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomCleaningStatus extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(SetRoomCleaningStatus.class);
    private Boolean isCleaning;


    public SetRoomCleaningStatus(HotelFacade facade, Boolean isCleaning) {
        this.facade = facade;
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
                logger.info("Room " + room.getNumber() + " is cleaned");
            } else {
                logger.info("Room " + room.getNumber() + " cleaned");

            }

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
