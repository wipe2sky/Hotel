package com.hotel.ui.actions.room;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class GetRoomAvailableAfterDate extends AbstractAction implements IAction {
    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите дату для проверки в формате гггг-мм-дд");
            LocalDate date = LocalDate.parse(reader.readLine());

            System.out.println();
            facade.getRoomAvailableAfterDate(date).forEach(System.out::println);
            System.out.println();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
