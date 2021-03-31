package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetRoomAvailableAfterDate extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetRoomAvailableAfterDate.class.getName());

    public GetRoomAvailableAfterDate(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите дату для проверки в формате гггг-мм-дд");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime date = LocalDateTime.parse(reader.readLine() + " 00:00", formatter);

            facade.getRoomAvailableAfterDate(date).forEach(System.out::println);
        }catch (NumberFormatException |IOException e){
            logger.log(Logger.Level.WARNING, "Get room available after date failed", e);
        }
    }
}
