package com.hotel.ui.actions.history;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetLast3GuestInRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id номера:");
            Integer roomId = Integer.parseInt(reader.readLine());

            System.out.println();
            facade.getLast3GuestInRoom(roomId).forEach(System.out::println);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
