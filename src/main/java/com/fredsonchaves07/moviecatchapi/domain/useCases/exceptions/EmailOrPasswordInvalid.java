package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public class EmailOrPasswordInvalid extends RuntimeException {

    public EmailOrPasswordInvalid(String message) {
        super(message);
    }
}
