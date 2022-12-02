package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public class InvalidTokenException extends DomainException {

    private static final String type = "InvalidTokenError";
    private static final String title = "Token invalid";
    private static final String detail = "Check token credentials and try again";

    public InvalidTokenException() {
        super(type, title, detail);
    }
}
