package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class UserNotFoundException extends DomainException {

    private static final String TYPE = "UserNotFoundError";

    private static final String TITLE = "User not found";

    private static final String DETAIL = "User not found check registered email";

    public UserNotFoundException() {
        super(TYPE, TITLE, DETAIL);
    }
}
