package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.exceptions.NotFoundEntityException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundEntityException.class)
    protected ResponseEntity<String> handleNotFoundEntityExceptionException(NotFoundEntityException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<String> handleServiceException(ServiceException ex){
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
