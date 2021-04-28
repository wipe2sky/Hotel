package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.NotFoundEntityException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundEntityException.class)
    protected ResponseEntity<String> handleNotFoundEntityExceptionException(NotFoundEntityException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DaoException.class)
    protected ResponseEntity<String> handleDaoException(DaoException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleUnexpectedException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
