package com.company.knowledgebasebackend.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String exception){
        super(exception);
    }
}
