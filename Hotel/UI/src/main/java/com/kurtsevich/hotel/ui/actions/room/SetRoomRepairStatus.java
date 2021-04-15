package com.kurtsevich.hotel.ui.actions.room;


import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class SetRoomRepairStatus extends AbstractAction implements IAction {
    private Boolean isRepair;


    public SetRoomRepairStatus(HotelFacade facade, Boolean isRepair) {
        this.facade = facade;
        this.isRepair = isRepair;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());
            Room room = facade.getRoomInfo(roomId);

            facade.setRoomRepairStatus(roomId, isRepair);

            if (isRepair) {
                log.info("Room " + room.getId() + " is repaired.");
            } else if(room.getStatus().equals(RoomStatus.REPAIR)){
                log.info("Room " + room.getId() + " repaired.");
            } else {
                log.info("Room " + room.getId() + " is not repaired.");
            }
        } catch (ServiceException | NumberFormatException |IOException e) {
            log.warn("Set room repair status failed", e);
        }
    }
}
