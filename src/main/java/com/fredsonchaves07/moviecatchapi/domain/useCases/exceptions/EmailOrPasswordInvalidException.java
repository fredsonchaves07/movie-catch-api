package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public class EmailOrPasswordInvalidException extends RuntimeException {

    public EmailOrPasswordInvalidException() {
        super("Email or password invalid.");
    }
}
