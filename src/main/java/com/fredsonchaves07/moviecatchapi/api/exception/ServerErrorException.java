package com.fredsonchaves07.moviecatchapi.api.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends ApiException {

    private static final int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final String type = "ServerError";
    private static final String title = "Server Error";
    private static final String detail = "Server error. Consult the administrator.";

    public ServerErrorException() {
        super(status, type, title, detail);
    }
}
