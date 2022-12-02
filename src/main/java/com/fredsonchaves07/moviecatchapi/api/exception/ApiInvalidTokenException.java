package com.fredsonchaves07.moviecatchapi.api.exception;

public class ApiInvalidTokenException extends ApiException {

    private static final int codStatus = 498;

    public ApiInvalidTokenException(String type, String title, String detail) {
        super(codStatus, type, title, detail);
    }
}
