package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public class SendEmailException extends RuntimeException {

    public SendEmailException(String message) {
        super(message);
    }
}
