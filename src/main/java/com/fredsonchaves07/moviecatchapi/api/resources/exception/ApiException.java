package com.fredsonchaves07.moviecatchapi.api.resources.exception;

public abstract class ApiException extends RuntimeException {

    private int status;
    private String type;
    private String title;
    private String detail;

    public ApiException(int codStatus, String type, String title, String detail) {
        super(detail);
        this.status = codStatus;
        this.type = type;
        this.title = title;
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}
