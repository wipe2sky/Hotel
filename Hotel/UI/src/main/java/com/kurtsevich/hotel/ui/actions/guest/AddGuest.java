package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class AddGuest extends AbstractAction implements IAction {

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

            log.info("Guest {} {} added", guest.getLastName(), guest.getFirstName());

        } catch (IOException e) {
            log.warn("Add guest failed", e);
        }
    }
}
