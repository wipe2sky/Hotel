package com.hotel.ui.actions.guest;

import com.hotel.model.Guest;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddGuest extends AbstractAction implements IAction {

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите фамилию гостя");
            String lastName = reader.readLine();
            System.out.println("Введите Имя гостя");
            String firstName = reader.readLine();
            Guest guest = facade.addGuest(lastName, firstName);

            System.out.println();
            System.out.println("Гость " + guest.getLastName() + " " + guest.getFirstName() + " успешно добавлен." + " Id - " + guest.getId());
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
