package com.test.trainee.exception;

public class BadRequestException extends Exception {

    public BadRequestException(final String exMessage) {
        super(exMessage);
    }
}
