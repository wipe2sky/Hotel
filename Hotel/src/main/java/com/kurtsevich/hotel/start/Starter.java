package com.kurtsevich.hotel.start;


import com.kurtsevich.hotel.ui.menu.MenuController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Starter {


    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ContextConfig.class);
        MenuController menuController = context.getBean("menuController", MenuController.class);

        menuController.run();

        context.close();

    }
}
