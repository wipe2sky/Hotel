package com.hotel.util;

import com.hotel.exceptions.ServiceException;

import java.io.Serializable;

public class IdGenerator implements Serializable {
    private static final Logger logger = new Logger(IdGenerator.class.getName());
    private static IdGenerator instance;

    private  Integer guestId = 1;
    private  Integer roomId = 1;
    private  Integer serviceId = 1;
    private  Integer historyId = 1;

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        if(instance == null) {
            try {
                instance = SerializationHandler.deserialize();
            } catch (ServiceException e) {
                logger.log(Logger.Level.WARNING, "Deserialization failed");
                instance = new IdGenerator();
            }
        }
        return instance;
    }

    public Integer generateGuestId() {
        return guestId++;
    }

    public Integer generateRoomId() {
        return roomId++;
    }

    public Integer generateServiceId() {
        return serviceId++;
    }
    public Integer generateHistoryId(){
        return historyId++;
    }



}
