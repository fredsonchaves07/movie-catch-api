package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class NameInvalidException extends DomainException {

    private static final String TYPE = "NameValidError";

    private static final String TITLE = "Name not provided or invalid";

    private static final String DETAIL = "The name field is required. " +
            "It was not informed in the request or it is invalid. " +
            "Consult the resource documentation to verify request details";

    public NameInvalidException() {
        super(TYPE, TITLE, DETAIL);
    }
}
