package com.kurtsevich.hotel.di.configurator;

import com.kurtsevich.hotel.di.ApplicationContext;
import com.kurtsevich.hotel.di.annotation.InjectByType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    private final Logger logger = LoggerFactory.getLogger(InjectByTypeAnnotationObjectConfigurator.class);
    @Override
    public void configure(Object t, ApplicationContext context) {

        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                try {
                    field.set(t, object);
                } catch (IllegalAccessException e) {
                    logger.warn("Unable to access the field", e);
                }

            }
        }
    }
}
