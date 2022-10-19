package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public class PasswordValidException extends DomainUseCaseException {

    private static final String type = "PasswordValidError";
    private static final String title = "Password not provided or invalid";
    private static final String detail = "The password field is required. " +
            "It was not informed in the request or it is invalid. " +
            "Consult the resource documentation to verify request details";

    public PasswordValidException() {
        super(type, title, detail);
    }
}
