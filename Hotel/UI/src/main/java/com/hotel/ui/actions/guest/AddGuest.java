package com.hotel.ui.actions.guest;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.Guest;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class AddGuest  implements IAction {
    private static final Logger logger = new Logger(AddGuest.class.getName());
    @InjectByType
    private HotelFacade facade;

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
