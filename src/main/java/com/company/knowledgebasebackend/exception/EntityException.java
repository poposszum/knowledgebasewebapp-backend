package com.company.knowledgebasebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityException extends RuntimeException {

    public EntityException(String exeption){
        super(exeption);
    }
}
