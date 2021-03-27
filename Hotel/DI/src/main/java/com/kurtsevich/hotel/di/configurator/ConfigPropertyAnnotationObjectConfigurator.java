package com.kurtsevich.hotel.di.configurator;

import com.kurtsevich.hotel.di.ApplicationContext;
import com.kurtsevich.hotel.di.annotation.ConfigProperty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ConfigPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private static final String DELIMITER = " *= *";
    private Map<String, String> propertiesMap;

    private String propertyPath;

    public ConfigPropertyAnnotationObjectConfigurator() {
        try {
            propertyPath = getDefaultPropertyPath();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Stream<String> lines = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(propertyPath));
            lines = reader.lines();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            propertiesMap = lines.map(line -> line.split(DELIMITER))
                    .collect(toMap(arr -> arr[0], arr -> arr[1]));
        } catch (Exception ignore) {
        }
    }

    private String getDefaultPropertyPath() throws NoSuchMethodException {
        return (String) ConfigProperty.class.getMethod("configName").getDefaultValue();
    }

    @Override
    public void configure(Object t, ApplicationContext context) {
        Class<?> impClass = t.getClass();

        for (Field field : impClass.getDeclaredFields()) {
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);


            if (annotation != null) {

                if (!annotation.configName().isEmpty() || !annotation.configName().equals(propertyPath)) {
                    propertyPath = annotation.configName();
                    try (Stream<String> lines = new BufferedReader(new FileReader(propertyPath)).lines()) {
                        propertiesMap = lines
                                .map(line -> line.split(DELIMITER))
                                .collect(toMap(arr -> arr[0], arr -> arr[1]));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                String propertyName = getPropertyName(annotation, field, t);
                field.setAccessible(true);
                String type;

                if (annotation.type().isEmpty()) {
                    type = field.getType().getSimpleName();
                } else {
                    type = annotation.type();
                }
                setField(type, field, t, propertyName);

            }
        }
    }

    private void setField(String type, Field field, Object t, String propertyName) {
        // Проверить на массив и коллекцию
//                if(field.getType().isArray())
        try {
            switch (type) {
                case "Byte" -> field.set(t, Byte.parseByte(propertyName));
                case "Short" -> field.set(t, Short.parseShort(propertyName));
                case "Integer" -> field.set(t, Integer.parseInt(propertyName));
                case "Long" -> field.set(t, Long.parseLong(propertyName));
                case "Float" -> field.set(t, Float.parseFloat(propertyName));
                case "Double" -> field.set(t, Double.parseDouble(propertyName));
                case "Charset" -> field.set(t, propertyName.charAt(0));
                case "Boolean" -> field.set(t, Boolean.getBoolean(propertyName));
                case "byte" -> field.setByte(t, Byte.parseByte(propertyName));
                case "short" -> field.setShort(t, Short.parseShort(propertyName));
                case "int" -> field.setInt(t, Integer.parseInt(propertyName));
                case "long" -> field.setLong(t, Long.parseLong(propertyName));
                case "float" -> field.setFloat(t, Float.parseFloat(propertyName));
                case "double" -> field.setDouble(t, Double.parseDouble(propertyName));
                case "char" -> field.setChar(t, propertyName.charAt(0));
                case "boolean" -> field.setBoolean(t, Boolean.parseBoolean(propertyName));

                default -> field.set(t, propertyName);
            }
        } catch (IllegalAccessException ignore) {
        }
//                }else {
//                    try {
//                        field.set(t, propertyName);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
    }

    private String getPropertyName(ConfigProperty annotation, Field field, Object t) {
        return annotation.propertyName().isEmpty()
                ? propertiesMap.get(t.getClass().getSimpleName().toLowerCase(Locale.ROOT) + "." + field.getName().toLowerCase(Locale.ROOT))
                : propertiesMap.get(annotation.propertyName());
    }
}