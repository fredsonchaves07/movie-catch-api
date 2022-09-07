package com.fredsonchaves07.moviecatchapi.api.resources.handler;

import com.fredsonchaves07.moviecatchapi.api.resources.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.api.resources.exception.MethodNotAllowedException;
import com.fredsonchaves07.moviecatchapi.api.resources.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(BadRequestException error) {
        StandardError standardError = getStandardError(error.getCodStatus(), error.getMessage());
        return ResponseEntity.status(standardError.getCodStatus()).body(standardError);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<StandardError> notFoundException() {
        ResourceNotFoundException notFoundException = new ResourceNotFoundException();
        StandardError standardError = getStandardError(notFoundException.getCodStatus(), notFoundException.getMessage());
        return ResponseEntity.status(standardError.getCodStatus()).body(standardError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> methodNotAllowed() {
        MethodNotAllowedException notAllowedException = new MethodNotAllowedException();
        StandardError standardError = getStandardError(notAllowedException.getCodStatus(), notAllowedException.getMessage());
        return ResponseEntity.status(standardError.getCodStatus()).body(standardError);
    }

    private StandardError getStandardError(int codStatus, String message) {
        StandardError standardError = new StandardError();
        standardError.setCodStatus(codStatus);
        standardError.setMessage(message);
        return standardError;
    }
}
