package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class EmailOrPasswordIncorrectException extends DomainException {

    private static final String TYPE = "EmailOrPasswordIncorrect";

    private static final String TITLE = "Email or password incorrect";

    private static final String DETAIL = "Invalid email or password. Please try again";

    public EmailOrPasswordIncorrectException() {
        super(TYPE, TITLE, DETAIL);
    }
}
