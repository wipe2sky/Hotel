package com.hotel.ui.actions.service;

import com.hotel.model.Guest;
import com.hotel.model.Service;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddServiceToGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            System.out.println("Введите id гостя");
            Integer guestId = Integer.parseInt(reader.readLine());
            Service service = facade.getServiceById(serviceId);
            Guest guest = facade.getGuestById(guestId);
            if(guest.isCheckIn()) {
                facade.addServiceToGuest(serviceId, guestId);
                System.out.println();
                System.out.println("Услуга " + service.getName()
                        + " успешно добавлена гостю " + guest.getLastName() + " " + guest.getFirstName());
                System.out.println();
            } else System.out.println("Постоялец с id "+ guest.getId()+ " не найден!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
