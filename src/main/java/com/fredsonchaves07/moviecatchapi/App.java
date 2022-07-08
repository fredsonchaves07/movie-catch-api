package com.fredsonchaves07.moviecatchapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class App {

    @Autowired
    HealthCheckAPI healthCheckAPI;

    @GetMapping()
    public HealthCheckAPI getHealthCheck() {
        return healthCheckAPI;
    }
}
