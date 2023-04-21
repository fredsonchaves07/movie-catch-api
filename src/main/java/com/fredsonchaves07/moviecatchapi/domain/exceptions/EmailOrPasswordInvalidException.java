package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class EmailOrPasswordInvalidException extends DomainException {

    private static final String TYPE = "EmailOrPasswordInvalidError";

    private static final String TITLE = "Email or password invalid";

    private static final String DETAIL = "" +
            "Invalid email or password. The password and email must contain mandatory criteria";

    public EmailOrPasswordInvalidException() {
        super(TYPE, TITLE, DETAIL);
    }
}
