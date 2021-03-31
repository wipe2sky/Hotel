package com.kurtsevich.hotel.ui.menu;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
@Singleton
public class Navigator {
    private final Logger logger = LoggerFactory.getLogger(MenuController.class.getName());

    private Menu currentMenu;

    @InjectByType
    public Navigator() {
    }

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
                logger.warn("Menu item with index " + index + " does not exist");
            }
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
