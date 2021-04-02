package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddGuest extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(AddGuest.class);

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

            logger.info("Guest {} {} added", guest.getLastName(), guest.getFirstName());

        } catch (IOException e) {
            logger.warn("Add guest failed", e);
        }
    }
}
