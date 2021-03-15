package com.hotel.ui.actions.guest;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.Guest;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class DeleteGuest  implements IAction {
    private static final Logger logger = new Logger(DeleteGuest.class.getName());
    @InjectByType
    private HotelFacade facade;
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя, которого необходимо удалить:");
            int guestId = Integer.parseInt(reader.readLine());
            Guest guest = facade.getGuestById(guestId);
            facade.deleteGuestById(guestId);

            logger.log(Logger.Level.INFO, "Guest " + guest.getLastName() + " " + guest.getFirstName() + " deleted");

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Deleting the guest failing", e);
        }
    }
}