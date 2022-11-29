package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public final class TokenExpiredOrInvalidException extends DomainUseCaseException {

    private static final String type = "TokenExpiredOrInvalidError";
    private static final String title = "Expired or invalid token";
    private static final String detail = "Check credentials and try again";

    public TokenExpiredOrInvalidException() {
        super(type, title, detail);
    }
}
