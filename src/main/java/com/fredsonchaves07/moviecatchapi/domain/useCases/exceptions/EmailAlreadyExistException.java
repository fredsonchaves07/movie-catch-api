package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public class EmailAlreadyExistException extends DomainUseCaseException {

    private static final String type = "EmailAlreadyExistError";
    private static final String title = "Email already exist";
    private static final String detail = "It is not possible to register a user with email " +
            "already registered in the system. " +
            "Try again with another email";

    public EmailAlreadyExistException() {
        super(type, title, detail);
    }
}
