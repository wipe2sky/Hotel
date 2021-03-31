package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomRepairStatus extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(SetRoomRepairStatus.class.getName());
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
                logger.log(Logger.Level.INFO, "Room " + room.getId() + " is repaired.");
            } else if(room.getStatus().equals(RoomStatus.REPAIR)){
                logger.log(Logger.Level.INFO, "Room " + room.getId() + " repaired.");
            } else {
                logger.log(Logger.Level.INFO, "Room " + room.getId() + " is not repaired.");
            }
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Set room repair status failed", e);
        }
    }
}
