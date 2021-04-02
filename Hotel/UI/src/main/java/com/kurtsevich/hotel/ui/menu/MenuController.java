package com.kurtsevich.hotel.ui.menu;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final Builder builder;
    private final Navigator navigator;
    private int index = -1;


    @InjectByType
    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.err.println();
            builder.buildMenu();
            navigator.setCurrentMenu(builder.getRootMenu());
            do {
                navigate(reader);
            } while (index != 0);
        } catch (IOException e) {
            logger.error("Menu Controller error", e);
        }
    }

    private void navigate(BufferedReader reader) throws IOException {
        try {
            navigator.printMenu();
            index = Integer.parseInt(reader.readLine());
            navigator.navigate(index);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            logger.warn("Menu Controller error", e);
        }
    }

}
