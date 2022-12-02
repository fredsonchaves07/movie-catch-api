package com.fredsonchaves07.moviecatchapi.api.exception;

import org.springframework.http.HttpStatus;

public class ApiExpiredTokenException extends ApiException {

    private static final int codStatus = HttpStatus.UNAUTHORIZED.value();

    public ApiExpiredTokenException(String type, String title, String detail) {
        super(codStatus, type, title, detail);
    }
}
