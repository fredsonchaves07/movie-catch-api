package com.fredsonchaves07.moviecatchapi.api.resources.handler;

import com.fredsonchaves07.moviecatchapi.api.resources.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.api.resources.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(BadRequestException error) {
        StandardError standardError = new StandardError();
        standardError.setCodStatus(error.getCodStatus());
        standardError.setMessage(error.getMessage());
        return ResponseEntity.status(standardError.getCodStatus()).body(standardError);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<StandardError> notFoundException() {
        StandardError standardError = new StandardError();
        ResourceNotFoundException notFoundException = new ResourceNotFoundException();
        standardError.setCodStatus(notFoundException.getCodStatus());
        standardError.setMessage(notFoundException.getMessage());
        return ResponseEntity.status(standardError.getCodStatus()).body(standardError);
    }
}
