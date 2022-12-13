package com.fredsonchaves07.moviecatchapi.domain.exceptions;

public abstract class DomainException extends RuntimeException {

    private final String type;

    private final String title;

    public DomainException(String type, String title, String detail) {
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
