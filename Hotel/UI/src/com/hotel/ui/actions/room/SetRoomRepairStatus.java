package com.hotel.ui.actions.room;

import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomRepairStatus extends AbstractAction implements IAction {
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
                System.out.println();
                System.out.println("Комната " + room.getNumber() + " поставлена на ремонт");
                System.out.println();
            } else if(room.getStatus().equals(RoomStatus.ON_REPAIR)){
                System.out.println();
                System.out.println("Комната " + room.getNumber() + " снята с ремонта");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Комната " + room.getNumber() + " не на ремонте");
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
