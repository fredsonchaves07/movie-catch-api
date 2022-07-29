package com.fredsonchaves07.moviecatchapi.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ApplicationConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
