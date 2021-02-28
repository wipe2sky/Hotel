package com.hotel.util;


import com.hotel.exceptions.ServiceException;
import com.hotel.model.AEntity;
import com.hotel.util.generator.IdGenerator;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class SerializationHandler {
    private static final Logger logger = new Logger(SerializationHandler.class.getName());
    private static final String PATH_TO_FILE = PropertiesHandler.getProperty("hotel.server.serialization.path_to_file")
            .orElseThrow(() -> new ServiceException("Serialization file not found"));
    private static final String PATH_TO_ID_GENERATOR = PropertiesHandler.getProperty("hotel.server.serialization.path_to_id_generator")
            .orElseThrow(() -> new ServiceException("Serialization file not found"));
    private static List<List<? extends AEntity>> deserializeList;
    private static IdGenerator idGenerator;

    private SerializationHandler() {
    }

    public static void serialize(List<? extends AEntity>... entity) {
        List<List<? extends AEntity>> serializeList = List.of(entity);
        try (FileOutputStream fos = new FileOutputStream(PATH_TO_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(serializeList);
            logger.log(Logger.Level.INFO, "Serialization completed");
        } catch (IOException e) {
            logger.log(Logger.Level.WARNING, "Serialization failed.", e);
            throw new ServiceException("Serialization failed", e);
        }
    }

    public static void serialize(IdGenerator generator) {
        idGenerator = generator;
        try (FileOutputStream fos = new FileOutputStream(PATH_TO_ID_GENERATOR);
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(idGenerator);
            logger.log(Logger.Level.INFO, "Id generator serialization completed");
        } catch (IOException e) {
            logger.log(Logger.Level.WARNING, "Id generator serialization failed.", e);
            throw new ServiceException("Id generator serialization failed", e);
        }
    }

    private static void loadFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH_TO_FILE))) {
            deserializeList = (List<List<? extends AEntity>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed.", e);
            throw new ServiceException("Deserialization failed", e);
        }
    }

    private static void loadIdGenerator() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH_TO_ID_GENERATOR))) {
            idGenerator = (IdGenerator) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed.", e);
            throw new ServiceException("Deserialization failed", e);
        }
    }

    public static <T> List<T> deserialize(Class clazz) {
        if (deserializeList == null) loadFile();

        if (deserializeList != null) {
            for (List<? extends AEntity> entities :
                    deserializeList) {
                if (!entities.isEmpty() && entities.get(0).getClass().equals(clazz)) {
                    logger.log(Logger.Level.INFO, "Deserialization " + clazz.getSimpleName() + " completed");

                    return (List<T>) entities;
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    public static IdGenerator deserialize() {
        if (idGenerator == null) loadIdGenerator();

        if (idGenerator != null) {
            logger.log(Logger.Level.INFO,
                    "Deserialization " + IdGenerator.class.getSimpleName() + " completed");

            return idGenerator;
        }
        return null;
    }

}
