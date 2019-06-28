package com.company.knowledgebasebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CostumExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExeptions(Exception exeption, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exeption.getMessage(), webRequest.getDescription(false));

            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundExeption(EntityException unf, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), unf.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


}
