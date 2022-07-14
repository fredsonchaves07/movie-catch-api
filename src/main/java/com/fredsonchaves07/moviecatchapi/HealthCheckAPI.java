package com.fredsonchaves07.moviecatchapi;

import org.springframework.stereotype.Component;

@Component
public class HealthCheckAPI {

    private String status = "OK";
    private String message = "API running";

    public HealthCheckAPI() {

    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
