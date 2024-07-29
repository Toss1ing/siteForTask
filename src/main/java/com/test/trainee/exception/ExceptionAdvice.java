package com.test.trainee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class ExceptionAdvice {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExceptionResponse> objectNotFoundException(final ObjectNotFoundException exMessage) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exMessage.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(final BadRequestException exMessage) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exMessage.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectExistException.class)
    public ResponseEntity<ExceptionResponse> objectExistException(final ObjectExistException exMessage) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exMessage.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> globalExceptionHandler(final Exception exMessage) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exMessage.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
