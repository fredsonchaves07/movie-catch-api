package com.fredsonchaves07.moviecatchapi.domain.service.mail;

import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;

public interface SendEmailService {

    void send(MessageEmailDTO message, String template) throws SendEmailException;

    default boolean isEmailValid(MessageEmailDTO messageEmailDTO) {
        return messageEmailDTO != null &&
                messageEmailDTO.subject() != null &&
                messageEmailDTO.email() != null;
    }
}
