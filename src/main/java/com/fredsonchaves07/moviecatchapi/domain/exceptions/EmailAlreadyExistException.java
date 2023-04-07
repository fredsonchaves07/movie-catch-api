package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class EmailAlreadyExistException extends DomainException {

    private static final String TYPE = "EmailAlreadyExistError";

    private static final String TITLE = "Email already exist";

    private static final String DETAIL = "It is not possible to register a user with email " +
            "already registered in the system. " +
            "Try again with another email";

    public EmailAlreadyExistException() {
        super(TYPE, TITLE, DETAIL);
    }
}
