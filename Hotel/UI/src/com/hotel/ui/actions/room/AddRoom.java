package com.hotel.ui.actions.room;

import com.hotel.model.Room;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите номер комнаты");
            Integer number = Integer.parseInt(reader.readLine());
            System.out.println("Введите вместимость");
            Integer capacity = Integer.parseInt(reader.readLine());
            System.out.println("Введите звёздность комнаты");
            Integer stars = Integer.parseInt(reader.readLine());
            System.out.println("Введите стоимость");
            Float price = Float.parseFloat(reader.readLine());
            Room room = facade.addRoom(number, capacity, stars, price);
            System.out.println();
            System.out.println("Комната успешно добавлена " + room);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
