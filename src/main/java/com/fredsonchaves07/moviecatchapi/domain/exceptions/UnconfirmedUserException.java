package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class UnconfirmedUserException extends DomainException {

    private static final String TYPE = "UncofirmedUserError";

    private static final String TITLE = "User has not been confirmed";

    private static final String DETAIL = "User has not been confirmed. Check your confirmation email and try again";

    public UnconfirmedUserException() {
        super(TYPE, TITLE, DETAIL);
    }
}
