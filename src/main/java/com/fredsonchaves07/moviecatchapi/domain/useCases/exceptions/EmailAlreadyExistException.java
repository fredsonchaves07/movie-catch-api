package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException() {
        super("Email already exist");
    }
}
