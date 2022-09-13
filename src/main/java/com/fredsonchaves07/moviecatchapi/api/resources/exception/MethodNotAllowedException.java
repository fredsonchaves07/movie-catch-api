package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends ApiException {

    private static final int status = HttpStatus.METHOD_NOT_ALLOWED.value();
    private static final String type = "MethodNotAllowedError";
    private static final String title = "Method not allowed";
    private static final String detail = "\n" +
            "The resource you are trying to access does " +
            "not allow requests of this type. See available feature documentation.";

    public MethodNotAllowedException() {
        super(status, type, title, detail);
    }
}
