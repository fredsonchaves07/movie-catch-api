package com.fredsonchaves07.moviecatchapi.api.config;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "docker"})
public class DevConfiguration {

    @Bean
    @Primary
    public FakeSendMailService fakeSendMailImpl() {
        return new FakeSendMailService();
    }
}
