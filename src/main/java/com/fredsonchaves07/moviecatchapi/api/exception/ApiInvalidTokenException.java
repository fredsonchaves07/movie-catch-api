package com.fredsonchaves07.moviecatchapi.api.exception;

import com.fredsonchaves07.moviecatchapi.domain.exceptions.DomainException;

public class ApiInvalidTokenException extends ApiException {

    private static final int codStatus = 498;

    public ApiInvalidTokenException(String type, String title, String detail) {
        super(codStatus, type, title, detail);
    }

    public ApiInvalidTokenException(DomainException exception) {
        this(exception.getType(), exception.getTitle(), exception.getMessage());
    }
}
