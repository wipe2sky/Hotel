package com.hotel.factory.configurator;

import com.hotel.factory.ApplicationContext;
import com.hotel.factory.annotation.InjectProperty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private Map<String, String> propertiesMap;

    public InjectPropertyAnnotationObjectConfigurator() {
//        String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
        String path = "./DI/src/main/resources/application.properties";

        Stream<String> lines = null;
        try {
            lines = new BufferedReader(new FileReader(path)).lines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        propertiesMap = lines.map(line -> line.split(" *= *"))
                .collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    public void configure(Object t, ApplicationContext context) {
        Class<?> impClass = t.getClass();

        for (Field field : impClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);


            if (annotation != null) {
                String value = annotation.value().isEmpty()
                        ? propertiesMap.get(field.getName())
                        : propertiesMap.get(annotation.value());
                field.setAccessible(true);
                try {
                    field.set(t, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
