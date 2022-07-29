package com.fredsonchaves07.moviecatchapi.application.doubles;

import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.service.exception.SendEmailException;

public class FakeSendMail implements SendEmailService {

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
