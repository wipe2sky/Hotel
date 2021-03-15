package com.hotel.ui.menu;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.util.IdGenerator;
import com.hotel.server.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class MenuController {
    private static final Logger logger = new Logger(MenuController.class.getName());

//    private static MenuController instance;
    @InjectByType
    private  Builder builder;
    @InjectByType
    private  Navigator navigator;

//
//    public MenuController() {
//        this.navigator = Navigator.getInstance();
//        this.builder = Builder.getInstance();
//    }
//    public static MenuController getInstance() {
//        if(instance == null) instance = new MenuController();
//        return instance;
//    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.err.println( );
//            IdGenerator.getInstance();
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
