package com.kurtsevich.hotel.start;


import com.kurtsevich.hotel.di.Application;
import com.kurtsevich.hotel.di.ApplicationContext;
import com.kurtsevich.hotel.server.util.DBConnector;
import com.kurtsevich.hotel.ui.menu.MenuController;

import java.util.HashMap;


public class Starter {


    public static void main(String[] args) {

        ApplicationContext context = Application.run("com.kurtsevich.hotel", new HashMap<>());
        DBConnector connection = context.getObject(DBConnector.class);
        connection.open();

        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();

        connection.close();

    }
}
