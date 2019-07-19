package com.company.knowledgebasebackend.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorModel {
    private Date timestamp;
    private String message;
    private String details;

}
