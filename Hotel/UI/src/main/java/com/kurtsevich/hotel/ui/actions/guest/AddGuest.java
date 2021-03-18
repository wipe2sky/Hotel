package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddGuest extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(AddGuest.class.getName());

    public AddGuest(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите Фамилию гостя");
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
