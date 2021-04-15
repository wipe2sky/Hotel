package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class SetRoomCleaningStatus extends AbstractAction implements IAction {
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
                log.info("Room {} is cleaned", room.getNumber());
            } else {
                log.info("Room {} cleaned", room.getNumber());

            }

        } catch (ServiceException | NumberFormatException |IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
