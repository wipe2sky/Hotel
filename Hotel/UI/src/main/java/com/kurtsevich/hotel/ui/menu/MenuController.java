package com.kurtsevich.hotel.ui.menu;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class MenuController {
    private static final Logger logger = new Logger(MenuController.class.getName());

    private final Builder builder;
    private final Navigator navigator;

@InjectByType
    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.err.println( );
            builder.buildMenu();
            navigator.setCurrentMenu(builder.getRootMenu());
            int index = -1;
            do {
                try {
                    navigator.printMenu();
                    index = Integer.parseInt(reader.readLine());
                    navigator.navigate(index);
                } catch (NumberFormatException| IndexOutOfBoundsException e) {
                    logger.log(Logger.Level.WARNING, "Menu Controller error", e);
                }
            } while (index != 0);
        } catch (IOException e) {
            logger.log(Logger.Level.ERROR, "Menu Controller error", e);
        }
    }

}
