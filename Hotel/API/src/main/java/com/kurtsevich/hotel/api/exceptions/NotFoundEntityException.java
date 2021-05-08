package com.kurtsevich.hotel.api.exceptions;

public class NotFoundEntityException extends RuntimeException{
    public NotFoundEntityException(Integer id){
        super("Couldn't find entity by id = " + id);
    }
    public NotFoundEntityException(String name){
        super("Couldn't find entity by name = " + name);
    }
}
