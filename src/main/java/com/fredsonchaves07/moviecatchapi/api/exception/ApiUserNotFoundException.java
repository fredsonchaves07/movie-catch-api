package com.fredsonchaves07.moviecatchapi.api.exception;

import org.springframework.http.HttpStatus;

public class ApiUserNotFoundException extends ApiException {

    private static final int codStatus = HttpStatus.NOT_FOUND.value();

    public ApiUserNotFoundException(String type, String title, String detail) {
        super(codStatus, type, title, detail);
    }
}
