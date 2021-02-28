package com.hotel.ui.menu;

import java.util.List;

public class Navigator {
    private static Navigator instance;
    private Menu currentMenu;

    private Navigator() {
    }

    public static Navigator getInstance() {
        if(instance == null) instance = new Navigator() ;
        return instance;
    }

    public void printMenu() {
        List<MenuItem> menuItem = currentMenu.getMenuItems();
        System.out.println("------------------------------------");
        System.out.println(currentMenu.getName());
        System.out.println("____________________________________");
        for (int i = 0; i < menuItem.size(); i++) {
            System.out.println(String.format("%d - %s", i, menuItem.get(i).getTitle()));
        }
        System.out.println("____________________________________");
    }

    public void navigate(Integer index) {
        if (currentMenu != null) {
            MenuItem menuItem = currentMenu.getMenuItems().get(index);
            menuItem.doAction();
            currentMenu = menuItem.getNextMenu();
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
