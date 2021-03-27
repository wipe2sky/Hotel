package com.kurtsevich.hotel.server.util;


import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.AEntity;

import java.io.*;
import java.util.Collections;
import java.util.List;
@Singleton
public class SerializationHandler {
    private static final Logger logger = new Logger(SerializationHandler.class.getName());
    @ConfigProperty
    private  String pathToSaveFile;
    @ConfigProperty
    private String pathToSaveIdGenerator;
    private  List<List<? extends AEntity>> deserializeList;
    private  IdGenerator idGenerator;

    @InjectByType
    public SerializationHandler() {
    }

    public void serialize(List<? extends AEntity>... entity) {
        List<List<? extends AEntity>> serializeList = List.of(entity);
        try (FileOutputStream fos = new FileOutputStream(pathToSaveFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(serializeList);
            logger.log(Logger.Level.INFO, "Serialization completed");
        } catch (IOException e) {
            logger.log(Logger.Level.WARNING, "Serialization failed.", e);
            throw new ServiceException("Serialization failed", e);
        }
    }

    public void serialize(IdGenerator generator) {
        idGenerator = generator;
        try (FileOutputStream fos = new FileOutputStream(pathToSaveIdGenerator);
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(idGenerator);
            logger.log(Logger.Level.INFO, "Id generator serialization completed");
        } catch (IOException e) {
            logger.log(Logger.Level.WARNING, "Id generator serialization failed.", e);
            throw new ServiceException("Id generator serialization failed", e);
        }
    }

    private void loadFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathToSaveFile))) {
            deserializeList = (List<List<? extends AEntity>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed.", e);
            throw new ServiceException("Deserialization failed", e);
        }
    }

    private void loadIdGenerator() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathToSaveIdGenerator))) {
            idGenerator = (IdGenerator) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed.", e);
            throw new ServiceException("Deserialization failed", e);
        }
    }

    public <T> List<T> deserialize(Class<T> clazz) {
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
        return Collections.emptyList();
    }

    public IdGenerator deserialize() {
        if (idGenerator == null) loadIdGenerator();

        if (idGenerator != null) {
            logger.log(Logger.Level.INFO,
                    "Deserialization " + IdGenerator.class.getSimpleName() + " completed");

            return idGenerator;
        }
        return null;
    }

}
