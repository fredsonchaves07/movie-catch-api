package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class InvalidTokenException extends DomainException {

    private static final String TYPE = "InvalidTokenError";

    private static final String TITLE = "Token invalid";

    private static final String DETAIL = "Check token credentials and try again";

    public InvalidTokenException() {
        super(TYPE, TITLE, DETAIL);
    }
}
