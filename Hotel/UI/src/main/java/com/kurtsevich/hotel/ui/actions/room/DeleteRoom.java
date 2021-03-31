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

public class DeleteRoom extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(DeleteRoom.class);

    public DeleteRoom(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());
            Room room = facade.getRoomInfo(roomId);

            facade.deleteRoom(roomId);
            logger.info("Room № " + room.getNumber() + " has deleted.");

        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.warn("Delete room failed", e);
        }
    }
}
