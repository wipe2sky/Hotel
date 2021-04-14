package com.kurtsevich.hotel.start;


import com.kurtsevich.hotel.ui.menu.MenuController;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Starter {


    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MenuController menuController = context.getBean("menuController", MenuController.class);

        menuController.run();

        context.close();

    }
}
