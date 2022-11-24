package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public final class UserAlreadyConfirmedException extends DomainUseCaseException {

    private static final String type = "UserAlreadyConfirmedError";
    private static final String title = "User has already been confirmed";
    private static final String detail = "User has already been confirmed. Login with your credentials. Click recover password if you forgot it";

    public UserAlreadyConfirmedException() {
        super(type, title, detail);
    }
}
