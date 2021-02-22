package com.hotel.ui.actions.room;

import com.hotel.model.Room;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetRoomCleaningStatus extends AbstractAction implements IAction {
    private boolean isCleaning;

    public SetRoomCleaningStatus(boolean isCleaning) {
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
                System.out.println();
                System.out.println("Комната " + room.getNumber() + " поставлена на уборку");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Комната " + room.getNumber() + " убрана");
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
