package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public class EmailValidException extends DomainUseCaseException {

    private static final String type = "EmailValidError";
    private static final String title = "Email not provided or invalid";
    private static final String detail = "The email field is required. " +
            "It was not informed in the request or it is invalid. " +
            "Consult the resource documentation to verify request details";

    public EmailValidException() {
        super(type, title, detail);
    }
}
