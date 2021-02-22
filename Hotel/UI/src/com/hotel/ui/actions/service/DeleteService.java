package com.hotel.ui.actions.service;

import com.hotel.model.Service;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteService extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            Service service = facade.getServiceById(serviceId);

            facade.deleteService(serviceId);

            System.out.println();
            System.out.println("Услуга " + service.getName() + " успешно удалена");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
