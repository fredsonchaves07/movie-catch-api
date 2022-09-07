package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends RuntimeException {

    private final int codStatus = HttpStatus.METHOD_NOT_ALLOWED.value();
    private static final String message = "Method not allowed.";

    public MethodNotAllowedException() {
        super(message);
    }

    public int getCodStatus() {
        return codStatus;
    }

    public String getMessage() {
        return message;
    }
}
