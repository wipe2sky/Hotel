package com.hotel.ui.actions.room;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomRepairStatus extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(SetRoomRepairStatus.class.getName());

    private boolean isRepair;

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
