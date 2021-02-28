package com.hotel.ui.menu;

import com.hotel.util.generator.IdGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuController {
    private static MenuController instance;
    private Builder builder;
    private Navigator navigator;

    private MenuController() {
        this.navigator = Navigator.getInstance();
        this.builder = Builder.getInstance();
    }

    public static MenuController getInstance() {
        if(instance == null) instance = new MenuController();
        return instance;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            builder.buildMenu();
            IdGenerator.getInstance();
            navigator.setCurrentMenu(builder.getRootMenu());
            navigator.printMenu();
            Integer index = Integer.parseInt(reader.readLine());

            while (index !=0) {
                navigator.navigate(index);
                navigator.printMenu();
                index = Integer.parseInt(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
