package com.hotel.ui.menu;

import com.hotel.factory.annotation.Singleton;
import com.hotel.server.util.Logger;

import java.util.List;
@Singleton
public class Navigator {
    private static final Logger logger = new Logger(MenuController.class.getName());

//    private static Navigator instance;
    private Menu currentMenu;

//    private Navigator() {
//    }

//    public static Navigator getInstance() {
//        if (instance == null) instance = new Navigator();
//        return instance;
//    }

    public void printMenu() {
        if (currentMenu != null) {
            List<MenuItem> menuItem = currentMenu.getMenuItems();
            System.out.println("------------------------------------");
            System.out.println(currentMenu.getName());
            System.out.println("____________________________________");
            for (int i = 0; i < menuItem.size(); i++) {
                System.out.println(String.format("%d - %s", i, menuItem.get(i).getTitle()));
            }
            System.out.println("____________________________________");
        }
    }

    public void navigate(Integer index) {
        if (currentMenu != null) {
            try {
                MenuItem menuItem = currentMenu.getMenuItems().get(index);
                menuItem.doAction();
                currentMenu = menuItem.getNextMenu();
            } catch (IndexOutOfBoundsException e) {
                logger.log(Logger.Level.WARNING, "Menu item with index " + index + " does not exist");
            }
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
