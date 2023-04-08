package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class SendEmailException extends RuntimeException {

    private static final String DETAIL = "Error sending email. Check email sending settings";

    public SendEmailException() {
        super(DETAIL);
    }
}
