package com.fredsonchaves07.moviecatchapi.api.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    private static final int codStatus = HttpStatus.NOT_FOUND.value();
    private static final String type = "ResourceNotFoundError";
    private static final String title = "Resource not found";

    public ResourceNotFoundException(String detail) {
        super(codStatus, type, title, detail);
    }
}
