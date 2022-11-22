package com.fredsonchaves07.moviecatchapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/")
@ApiIgnore
public class App {

    @Autowired
    HealthCheckAPI healthCheckAPI;

    @GetMapping()
    public HealthCheckAPI getHealthCheck() {
        return healthCheckAPI;
    }
}
