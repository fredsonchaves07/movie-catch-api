package com.fredsonchaves07.moviecatchapi.api.exception;

import com.fredsonchaves07.moviecatchapi.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    private final static int status = HttpStatus.UNAUTHORIZED.value();

    public UnauthorizedException(String type, String title, String detail) {
        super(status, type, title, detail);
    }

    public UnauthorizedException(DomainException exception) {
        this(exception.getType(), exception.getTitle(), exception.getMessage());
    }
}
