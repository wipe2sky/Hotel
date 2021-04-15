package com.kurtsevich.hotel.ui.menu;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class Navigator {

    private Menu currentMenu;

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
                log.warn("Menu item with index {} does not exist", index);
            }
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
