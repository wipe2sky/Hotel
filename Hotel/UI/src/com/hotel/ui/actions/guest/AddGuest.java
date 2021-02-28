package com.hotel.ui.actions.guest;

import com.hotel.model.Guest;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddGuest extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(AddGuest.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите фамилию гостя");
            String lastName = reader.readLine();
            System.out.println("Введите Имя гостя");
            String firstName = reader.readLine();
            Guest guest = facade.addGuest(lastName, firstName);

            logger.log(Logger.Level.INFO, "Guest " + guest.getLastName() + " " + guest.getFirstName() + " added." + " Id - " + guest.getId());

        } catch (IOException e) {
            logger.log(Logger.Level.WARNING, "Add guest failed", e);
        }
    }
}
