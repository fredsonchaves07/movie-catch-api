package com.fredsonchaves07.moviecatchapi.api.exception;

import com.fredsonchaves07.moviecatchapi.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    private static final int codStatus = HttpStatus.NOT_FOUND.value();
    private static final String type = "ResourceNotFoundError";
    private static final String title = "Resource not found";

    public ResourceNotFoundException(String detail) {
        super(codStatus, type, title, detail);
    }

    public ResourceNotFoundException(String type, String title, String detail) {
        super(codStatus, type, title, detail);
    }

    public ResourceNotFoundException(DomainException exception) {
        this(exception.getType(), exception.getTitle(), exception.getMessage());
    }
}
