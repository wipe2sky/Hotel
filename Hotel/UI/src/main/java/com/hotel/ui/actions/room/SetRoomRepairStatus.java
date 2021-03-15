package com.hotel.ui.actions.room;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.Room;
import com.hotel.server.model.RoomStatus;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class SetRoomRepairStatus implements IAction {
    private static final Logger logger = new Logger(SetRoomRepairStatus.class.getName());
    @InjectByType
    private HotelFacade facade;

    private boolean isRepair;

    public SetRoomRepairStatus() {
    }

    public SetRoomRepairStatus(boolean isRepair) {
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
            } else if(room.getStatus().equals(RoomStatus.ON_REPAIR)){
                logger.log(Logger.Level.INFO, "Room " + room.getId() + " repaired.");
            } else {
                logger.log(Logger.Level.INFO, "Room " + room.getId() + " is not repaired.");
            }
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Set room repair status failed", e);
        }
    }
}
