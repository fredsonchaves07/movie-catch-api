package com.fredsonchaves07.moviecatchapi.api.services.exception;

public class CreateUserUseCaseException extends RuntimeException {

    private String type;
    private String title;

    public CreateUserUseCaseException(String type, String title, String detail) {
        super(detail);
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
