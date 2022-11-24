package com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions;

public abstract class DomainUseCaseException extends RuntimeException {

    private final String type;
    private final String title;

    public DomainUseCaseException(String type, String title, String detail) {
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
