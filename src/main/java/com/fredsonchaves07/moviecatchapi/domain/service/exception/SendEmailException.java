package com.fredsonchaves07.moviecatchapi.domain.service.exception;

public class SendEmailException extends RuntimeException {

    public SendEmailException(String message) {
        super(message);
    }
}
