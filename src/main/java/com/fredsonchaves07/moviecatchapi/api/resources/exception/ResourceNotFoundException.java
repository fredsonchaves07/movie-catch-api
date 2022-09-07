package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    private final int codStatus = HttpStatus.NOT_FOUND.value();
    private static final String message = "URL Not Found.";

    public ResourceNotFoundException() {
        super(message);
    }

    public int getCodStatus() {
        return codStatus;
    }

    public String getMessage() {
        return message;
    }
}
