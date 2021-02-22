package com.hotel.ui.actions.room;

import com.hotel.model.Room;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeRoomPrice extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id комнаты");
            Integer roomId = Integer.parseInt(reader.readLine());
            System.out.println("Введите новую цену");
            Float price = Float.parseFloat(reader.readLine());

            facade.changeRoomPrice(roomId, price);
            Room room = facade.getRoomInfo(roomId);

            System.out.println();
            System.out.println("Стоимость проживания за сутки в комнате " + room.getNumber() + " успешно изменена на " + room.getPrice());
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
