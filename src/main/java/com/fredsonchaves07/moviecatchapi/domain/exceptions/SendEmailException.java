package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class SendEmailException extends RuntimeException {

    public SendEmailException(String message) {
        super(message);
    }
}
