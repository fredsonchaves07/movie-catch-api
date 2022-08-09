package com.fredsonchaves07.moviecatchapi.domain.service;

import com.fredsonchaves07.moviecatchapi.domain.service.exception.SendEmailException;

public interface SendEmailService {

    void send(String subject, String email, String content) throws SendEmailException;
}
