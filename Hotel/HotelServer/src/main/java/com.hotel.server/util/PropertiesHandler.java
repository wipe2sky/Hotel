package com.hotel.server.util;


import com.hotel.server.exceptions.ServiceException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class PropertiesHandler {
    private static final Logger logger = new Logger(PropertiesHandler.class.getName());

    private static Properties properties;
    private static final String PROPERTIES_FILE_PATH = "./HotelServer/src/main/resources/application.properties";

    private PropertiesHandler() {
    }

    public static Optional<String> getProperty(String key) {
        if (properties == null) loadProperties();
        return Optional.ofNullable(properties.getProperty(key));
    }

    private static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH)
        ) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            logger.log(Logger.Level.WARNING, "Read properties failed", e);
            throw new ServiceException("Read properties failed", e);
        }
    }
}
