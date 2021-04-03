package com.kurtsevich.hotel.di;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.PostConstruct;
import com.kurtsevich.hotel.di.configurator.ObjectConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private final ApplicationContext context;

    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        contextFiling(context);
    }

    private void contextFiling(ApplicationContext context) {
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            //Проверка является ли класс абстрактным, не факт что работает
            try {
                if (!Modifier.isAbstract(aClass.getModifiers())) {
                    configurators.add(aClass.getDeclaredConstructor().newInstance());
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error("Context filling error", e);
            }
        }

    }


    public <T> T createObject(Class<T> implClass) {

        T t = create(implClass);

        configure(t);

        invokeInit(implClass, t);

        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) {
        try {
            for (Method method : implClass.getMethods()) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    method.invoke(t);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Invoke init failed", e);
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configure(t, context));
    }

    private <T> T create(Class<T> implClass) {
        T t = null;
        for (Constructor<?> constructor : implClass.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(InjectByType.class)) {
                constructor.setAccessible(true);
                try {
                    t = (T) constructor.newInstance(getParams(constructor).toArray());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    logger.error("Crate class failed", e);
                }
            }
        }
        if(t == null) {
            try {
                t = implClass.getDeclaredConstructor().newInstance();
                return t;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error("Create class failed", e);
            }
        }
        return t;
    }

    private List<Object> getParams(Constructor<?> constructor) {
        List<Object> params = new ArrayList<>();
        for (Class<?> parameterType : constructor.getParameterTypes()) {
            params.add(context.getObject(parameterType));
        }
        return params;
    }
}
