package com.kurtsevich.hotel.server.api.exceptions;

public class NotFoundEntityException extends RuntimeException{
    public NotFoundEntityException(Integer id){
        super("Couldn't find entity by id = " + id);
    }
}
