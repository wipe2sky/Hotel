package com.hotel.ui.actions.service;

import com.hotel.model.Service;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddService extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите название услуги");
            String name = reader.readLine();
            System.out.println("Введите стоимость");
            Float price = Float.parseFloat(reader.readLine());
            Service service = facade.addService(name, price);
            System.out.println();
            System.out.println("Услуга \"" + service.getName() + "\" успешно добавлена");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
