package com.fredsonchaves07.moviecatchapi.api.resources.exception;

import org.springframework.http.HttpStatus;

public class MissingRequiredPropertyException extends ApiException {

    private static final int status = HttpStatus.BAD_REQUEST.value();
    private static final String type = "MissingPropertyError";
    private static final String title = "Mandatory properties not informed";
    private static final String detail = "One or more required properties were not reported. " +
            "Please check the mandatory parameters documentation of the request";

    public MissingRequiredPropertyException() {
        super(status, type, title, detail);
    }
}
