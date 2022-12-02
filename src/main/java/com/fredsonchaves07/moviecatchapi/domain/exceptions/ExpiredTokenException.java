package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public class ExpiredTokenException extends DomainException {

    private static final String type = "ExpiredTokenError";
    private static final String title = "Token expired";
    private static final String detail = "Check token credentials and try again";

    public ExpiredTokenException() {
        super(type, title, detail);
    }
}
