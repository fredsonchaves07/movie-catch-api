package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    private int codStatus = HttpStatus.BAD_REQUEST.value();
    private String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public int getCodStatus() {
        return codStatus;
    }

    public String getMessage() {
        return message;
    }
}
