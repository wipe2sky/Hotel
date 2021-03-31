package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomRepairStatus extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(SetRoomRepairStatus.class);
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
                logger.info("Room " + room.getId() + " is repaired.");
            } else if(room.getStatus().equals(RoomStatus.REPAIR)){
                logger.info("Room " + room.getId() + " repaired.");
            } else {
                logger.info("Room " + room.getId() + " is not repaired.");
            }
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("Set room repair status failed", e);
        }
    }
}
