package com.eshcherbinina.generalstore.exception.handler;

import com.eshcherbinina.generalstore.exception.EntityNotFoundException;
import com.eshcherbinina.generalstore.responses.ErrorDetails;
import com.eshcherbinina.generalstore.exception.UserNotAuthorized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
         ErrorDetails errorDetails = ErrorDetails.builder()
                 .timestamp(new Date())
                 .detail(ex.getDetails())
                 .status(HttpStatus.NOT_FOUND.value())
                 .type(ex.getType().toString())
                 .title(ex.getMessage())
                 .path(request.getDescription(false))
                 .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAuthorized.class)
    public final ResponseEntity<ErrorDetails> handleUserNotAuthorized(UserNotAuthorized ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .detail(ex.getDetails())
                .status(HttpStatus.FORBIDDEN.value())
                .type(ex.getType().toString())
                .title(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
}
