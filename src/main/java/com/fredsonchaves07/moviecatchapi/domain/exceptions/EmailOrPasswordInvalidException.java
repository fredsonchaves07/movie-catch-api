package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class EmailOrPasswordInvalidException extends DomainException {

    private static final String type = "EmailOrPasswordInvalidError";

    private static final String title = "Email or password invalid";

    private static final String detail = "Invalid email or password. The password and email must contain mandatory criteria";

    public EmailOrPasswordInvalidException() {
        super(type, title, detail);
    }
}
