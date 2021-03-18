package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomCleaningStatus extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(SetRoomCleaningStatus.class.getName());
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
                logger.log(Logger.Level.INFO, "Room " + room.getNumber() + " is cleaned");
            } else {
                logger.log(Logger.Level.INFO, "Room " + room.getNumber() + " cleaned");

            }

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, e.getMessage(), e);
        }
    }
}
