package com.fredsonchaves07.moviecatchapi.api.resources.handler;

public class StandardError {

    private int codStatus;
    private String message;

    public StandardError() {

    }

    public int getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(int codStatus) {
        this.codStatus = codStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
