package com.kurtsevich.hotel.server.util;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.exceptions.ServiceException;

import java.io.Serializable;

@Singleton
public class IdGenerator implements Serializable {
    private static final Logger logger = new Logger(IdGenerator.class.getName());
    private IdGenerator idGenerator;

    private Integer guestId = 1;
    private Integer roomId = 1;
    private Integer serviceId = 1;
    private Integer historyId = 1;

    @InjectByType
    public IdGenerator(SerializationHandler serializationHandler) {
        try {
            idGenerator = serializationHandler.deserialize();
            this.guestId = idGenerator.guestId;
            this.roomId = idGenerator.roomId;
            this.serviceId = idGenerator.serviceId;
            this.historyId = idGenerator.historyId;
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed");
        }
    }

//    @PostConstruct
//    public void init(){
//        try {
//            idGenerator = SerializationHandler.deserialize();
//            this.guestId = idGenerator.guestId;
//            this.roomId = idGenerator.roomId;
//            this.serviceId = idGenerator.serviceId;
//            this.historyId = idGenerator.historyId;
//        } catch (ServiceException e) {
//            logger.log(Logger.Level.WARNING, "Deserialization failed");
//        }
//    }
//    private IdGenerator() {
//
//    }

//    public static IdGenerator getInstance() {
//        if(instance == null) {
//            try {
//                instance = SerializationHandler.deserialize();
//            } catch (ServiceException e) {
//                logger.log(Logger.Level.WARNING, "Deserialization failed");
//                instance = new IdGenerator();
//            }
//        }
//        return instance;
//    }

    public Integer generateGuestId() {
        return guestId++;
    }

    public Integer generateRoomId() {
        return roomId++;
    }

    public Integer generateServiceId() {
        return serviceId++;
    }

    public Integer generateHistoryId() {
        return historyId++;
    }


}
