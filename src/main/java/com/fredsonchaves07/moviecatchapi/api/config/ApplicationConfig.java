package com.fredsonchaves07.moviecatchapi.api.config;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ApplicationConfig {

    @Bean()
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean()
    public FakeSendMail fakeSendMail() {
        return new FakeSendMail();
    }
}
