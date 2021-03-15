package com.hotel.factory;

import com.hotel.factory.configurator.JavaConfig;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packagesToScan, Map<Class, Class> ifc2ImplClass){
        JavaConfig config = new JavaConfig(packagesToScan, ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);

        context.setFactory(objectFactory);

        //Просканировать пакеты, найти все с аннотацие сигальтон и засунут в контекст Проверить работоспособность!!!
        //Нужна доп проверка в контексте, т.к. имлементирует и интерфейс, и его реализацию
//        config.getScanner().getTypesAnnotatedWith(Singleton.class).stream()
//                .forEach(t->context.getObject(t));
        return context;
    }
}
