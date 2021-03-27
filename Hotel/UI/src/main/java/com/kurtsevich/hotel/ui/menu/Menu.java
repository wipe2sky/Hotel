package com.kurtsevich.hotel.ui.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItems(MenuItem item) {
        menuItems.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(name, menu.name) && Objects.equals(menuItems, menu.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, menuItems);
    }
}
