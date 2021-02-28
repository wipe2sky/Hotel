package com.hotel.ui.actions.room;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class GetRoomAvailableAfterDate extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetRoomAvailableAfterDate.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите дату для проверки в формате гггг-мм-дд");
            LocalDate date = LocalDate.parse(reader.readLine());

            facade.getRoomAvailableAfterDate(date).forEach(System.out::println);
        }catch (NumberFormatException |IOException e){
            logger.log(Logger.Level.WARNING, "Get room available after date failed", e);
        }
    }
}
