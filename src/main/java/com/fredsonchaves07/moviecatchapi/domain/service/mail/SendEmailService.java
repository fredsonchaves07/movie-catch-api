package com.fredsonchaves07.moviecatchapi.domain.service.mail;

import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;

public interface SendEmailService {

    void send(String subject, String email, String content) throws SendEmailException;
}
