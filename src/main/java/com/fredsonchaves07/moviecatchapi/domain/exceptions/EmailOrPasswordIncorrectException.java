package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class EmailOrPasswordIncorrectException extends DomainException {

    private static final String type = "EmailOrPasswordIncorrect";

    private static final String title = "Email or password incorrect";

    private static final String detail = "Invalid email or password. Please try again";

    public EmailOrPasswordIncorrectException() {
        super(type, title, detail);
    }
}
