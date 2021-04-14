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
public class ChangeRoomPrice extends AbstractAction implements IAction {

    public ChangeRoomPrice(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());
            System.out.println("Введите новую цену");
            Double price = Double.parseDouble(reader.readLine());

            facade.changeRoomPrice(roomId, price);
            Room room = facade.getRoomInfo(roomId);
            log.info("Cost of living per day in the room {} has been changed to {}", room.getNumber(), room.getPrice());

        } catch (ServiceException | NumberFormatException | IOException e) {
            log.warn("Change room price failed", e);
        }
    }
}
