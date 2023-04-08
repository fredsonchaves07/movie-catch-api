package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class UserAlreadyConfirmedException extends DomainException {

    private static final String TYPE = "UserAlreadyConfirmedError";

    private static final String TITLE = "User has already been confirmed";

    private static final String DETAIL = "User has already been confirmed. Login with your credentials";

    public UserAlreadyConfirmedException() {
        super(TYPE, TITLE, DETAIL);
    }
}
