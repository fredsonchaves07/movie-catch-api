package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public final class NameInvalidException extends DomainUseCaseException {

    private static final String type = "NameValidError";
    private static final String title = "Name not provided or invalid";
    private static final String detail = "The name field is required. " +
            "It was not informed in the request or it is invalid. " +
            "Consult the resource documentation to verify request details";

    public NameInvalidException() {
        super(type, title, detail);
    }
}
