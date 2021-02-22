package com.hotel.ui.actions.guest;

import com.hotel.model.Guest;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя, которого необходимо удалить:");
            int guestId = Integer.parseInt(reader.readLine());
            Guest guest = facade.getGuestById(guestId);
            facade.deleteGuestById(guestId);

            System.out.println();
            System.out.println("Гость " + guest.getLastName() + " " + guest.getFirstName() + " успешно удалён");
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
