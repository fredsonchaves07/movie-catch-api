package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

    private final static int status = HttpStatus.BAD_REQUEST.value();
    private final static String type = "BadRequestError";
    private final static String title = "Bad Request";

    public BadRequestException(String detail) {
        super(status, type, title, detail);
    }
}
