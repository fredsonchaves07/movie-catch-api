package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class ExpiredTokenException extends DomainException {

    private static final String TYPE = "ExpiredTokenError";

    private static final String TITLE = "Token expired";

    private static final String DETAIL = "Check token credentials and try again";

    public ExpiredTokenException() {
        super(TYPE, TITLE, DETAIL);
    }
}
