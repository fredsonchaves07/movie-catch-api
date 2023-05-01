package com.fredsonchaves07.moviecatchapi.api.config.application;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev"})
public class DevConfiguration {

    @Bean
    @Primary
    public FakeSendMailService fakeSendMailImpl() {
        return new FakeSendMailService();
    }
}
