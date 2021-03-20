package com.kurtsevich.hotel.ui.menu;

import com.kurtsevich.hotel.ui.actions.IAction;

import java.util.Objects;

public class MenuItem {
    private String title;
    private IAction action;
    private Menu nextMenu;

    public MenuItem(String title, IAction action, Menu nextMenu) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
    }

    public void doAction() {
        action.execute();
    }

    public String getTitle() {
        return title;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(title, menuItem.title) && Objects.equals(action, menuItem.action) && Objects.equals(nextMenu, menuItem.nextMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, action, nextMenu);
    }
}
