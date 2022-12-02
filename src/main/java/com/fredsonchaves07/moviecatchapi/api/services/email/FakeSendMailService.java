package com.fredsonchaves07.moviecatchapi.api.services.email;

import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile({"dev, test", "docker"})
public class FakeSendMailService implements SendEmailService {

    private String supportMail = "admin@moviecatch.com";

    @Override
    public void send(String subject, String email, String content) throws SendEmailException {
        System.out.println("Send fake mail");
        System.out.println("From: " + supportMail);
        System.out.println("Subject: " + subject);
        System.out.println("To: " + email);
        System.out.println("Content: " + content);
    }
}
