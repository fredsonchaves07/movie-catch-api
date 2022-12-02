package com.fredsonchaves07.moviecatchapi.api.exception;

import org.springframework.http.HttpStatus;

public class UnknownPropertyInvalidException extends ApiException {

    private static final int status = HttpStatus.BAD_REQUEST.value();
    private static final String type = "UnknownPropertyError";
    private static final String title = "Invalid reported request property";
    private static final String detail = "One or more invalid properties were reported. " +
            "Check the request documentation for mandatory and optional parameters";

    public UnknownPropertyInvalidException() {
        super(status, type, title, detail);
    }
}
