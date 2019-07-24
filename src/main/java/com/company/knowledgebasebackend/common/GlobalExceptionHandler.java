package com.company.knowledgebasebackend.common;

import com.company.knowledgebasebackend.article.ArticleException;
import com.company.knowledgebasebackend.auth.AuthException;
import com.company.knowledgebasebackend.user.UserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Pushes the exceptions into the ErrorModel.
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ErrorModel> handleBadCredentialsException(BadCredentialsException e, WebRequest webRequest){
        ErrorModel errorModel = new ErrorModel(new Date(), e.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public final ResponseEntity<ErrorModel> handleAuthException(AuthException e, WebRequest webRequest){
        ErrorModel errorModel = new ErrorModel(new Date(), e.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<ErrorModel> handleUserException(UserException e, WebRequest webRequest){
        ErrorModel errorModel = new ErrorModel(new Date(), e.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArticleException.class)
    public final ResponseEntity<ErrorModel> handleArticleException(ArticleException e, WebRequest webRequest){
        ErrorModel errorModel = new ErrorModel(new Date(), e.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorModel errorDetails = new ErrorModel(new Date(), e.getBindingResult().getFieldError().getDefaultMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
