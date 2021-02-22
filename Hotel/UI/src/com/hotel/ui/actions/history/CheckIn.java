package com.hotel.ui.actions.history;

import com.hotel.model.Guest;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class CheckIn extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id гостя:");
            Integer guestId = Integer.parseInt(reader.readLine());
            System.out.println("Введите id номера:");
            Integer roomId = Integer.parseInt(reader.readLine());
            System.out.println("Введите дату для заезда в формате гггг-мм-дд");
            LocalDate checkInDate = LocalDate.parse( reader.readLine());
            System.out.println("Введите дату для заезда в формате гггг-мм-дд");
            LocalDate checkOutDate = LocalDate.parse( reader.readLine());

            facade.checkIn(guestId, roomId, checkInDate, checkOutDate);
            Guest guest = facade.getGuestById(guestId);

            System.out.println();
            System.out.println("Гость " + guest.getLastName() + " " + guest.getFirstName()
                    +" заселён в номер " + guest.getRoom().getNumber());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
