package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public class EmailOrPasswordInvalidException extends RuntimeException {

    public EmailOrPasswordInvalidException(String message) {
        super(message);
    }
}
