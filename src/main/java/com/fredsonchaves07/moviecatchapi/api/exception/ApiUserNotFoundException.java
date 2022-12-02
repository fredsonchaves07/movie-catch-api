package com.fredsonchaves07.moviecatchapi.api.exception;

public class ApiUserNotFoundException extends ResourceNotFoundException {

    public ApiUserNotFoundException(String type, String title, String detail) {
        super(type, title, detail);
    }
}
