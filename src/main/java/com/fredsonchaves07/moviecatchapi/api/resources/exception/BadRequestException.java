package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

    private final static int status = HttpStatus.BAD_REQUEST.value();

    public BadRequestException(String type, String title, String detail) {
        super(status, type, title, detail);
    }
}
