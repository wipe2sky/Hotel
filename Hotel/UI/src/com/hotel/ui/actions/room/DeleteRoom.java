package com.hotel.ui.actions.room;

import com.hotel.model.Room;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());;
            Room room = facade.getRoomInfo(roomId);
            facade.deleteRoom(roomId);
            System.out.println();
            System.out.println("Комната " + room.getNumber() + " успешно удалена");
            System.out.println();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
