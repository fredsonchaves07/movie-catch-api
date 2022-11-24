package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public final class UserNotFoundException extends DomainUseCaseException {

    private static final String type = "UserNotFoundError";
    private static final String title = "User not found";
    private static final String detail = "User not found check registered email";

    public UserNotFoundException() {
        super(type, title, detail);
    }
}
