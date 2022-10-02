package com.villa.resident.controller;

import com.villa.resident.exception.ResidentNotCreatedException;
import com.villa.resident.exception.ResidentNotDeletedException;
import com.villa.resident.exception.ResidentNotFoundException;
import com.villa.resident.exception.ResidentNotUpdatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResidentControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResidentNotFoundException.class)
    protected ResponseEntity<String> handleResidentNotFound(ResidentNotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResidentNotCreatedException.class)
    protected ResponseEntity<String> handleResidentNotCreated(ResidentNotCreatedException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResidentNotUpdatedException.class)
    protected ResponseEntity<String> handleResidentNotUpdated(ResidentNotUpdatedException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResidentNotDeletedException.class)
    protected ResponseEntity<String> handleResidentNotDeleted(ResidentNotDeletedException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
