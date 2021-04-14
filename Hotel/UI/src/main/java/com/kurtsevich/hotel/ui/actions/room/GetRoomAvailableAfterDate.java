package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class GetRoomAvailableAfterDate extends AbstractAction implements IAction {

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
            log.warn("Get room available after date failed", e);
        }
    }
}
