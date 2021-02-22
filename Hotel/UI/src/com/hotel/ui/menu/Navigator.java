package com.hotel.ui.menu;

import java.util.List;
import java.util.Objects;

public class Navigator {
    private static Navigator instance;
    private Menu currentMenu;

    private Navigator() {
    }

    public static Navigator getInstance() {
        return Objects.requireNonNullElse(instance, new Navigator());
    }

    public void printMenu() {
        List<MenuItem> menuItem = currentMenu.getMenuItems();
        System.out.println(currentMenu.getName());
        System.out.println("____________________________________");
        for (int i = 0; i < menuItem.size(); i++) {
            System.out.println(String.format("%d - %s", i, menuItem.get(i).getTitle()));
        }
        System.out.println("------------------------------------");
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
