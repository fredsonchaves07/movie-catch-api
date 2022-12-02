package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class UserAlreadyConfirmedException extends DomainException {

    private static final String type = "UserAlreadyConfirmedError";
    private static final String title = "User has already been confirmed";
    private static final String detail = "User has already been confirmed. Login with your credentials. Click recover password if you forgot it";

    public UserAlreadyConfirmedException() {
        super(type, title, detail);
    }
}
