package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public final class SendEmailException extends RuntimeException {

    private static final String type = "SendMailError";
    private static final String title = "Error sending email";
    private static final String detail = "Error sending email. Check email sending settings";

    public SendEmailException() {
        super(detail);
    }
}
