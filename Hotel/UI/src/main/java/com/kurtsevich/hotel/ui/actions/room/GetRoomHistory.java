package com.kurtsevich.hotel.ui.actions.room;


import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class GetRoomHistory extends AbstractAction implements IAction {

    public GetRoomHistory(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());
            log.info("History of room № {}", roomId);
            facade.getRoomHistory(roomId)
                    .forEach(System.out::println);


        } catch (ServiceException | NumberFormatException | IOException e) {
            log.warn("Get room history failed", e);
        }
    }
}
