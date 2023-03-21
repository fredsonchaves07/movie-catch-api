package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class UnconfirmedUserException extends DomainException {

    private static final String type = "UncofirmedUserError";

    private static final String title = "User has not been confirmed";

    private static final String detail = "User has not been confirmed. Check your confirmation email and try again";

    public UnconfirmedUserException() {
        super(type, title, detail);
    }
}
