package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends RuntimeException {

    private final int codStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final String message = "Server error. Consult the administrator.";

    public ServerErrorException() {
        super(message);
    }

    public int getCodStatus() {
        return codStatus;
    }

    public String getMessage() {
        return message;
    }
}
