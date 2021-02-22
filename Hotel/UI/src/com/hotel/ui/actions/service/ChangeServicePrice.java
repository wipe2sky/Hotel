package com.hotel.ui.actions.service;

import com.hotel.model.Service;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeServicePrice extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            System.out.println("Введите новую стоимость");
            Float price = Float.parseFloat(reader.readLine());
            facade.changeServicePrice(serviceId, price);
            Service service = facade.getServiceById(serviceId);
            System.out.println();
            System.out.println("Цена услуги " + service.getName() + " успешно изменена на " + price);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
