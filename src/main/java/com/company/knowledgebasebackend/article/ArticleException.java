package com.company.knowledgebasebackend.article;

public class ArticleException extends Exception {
    public ArticleException() {
    }

    public ArticleException(String message) {
        super(message);
    }

    public ArticleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleException(Throwable cause) {
        super(cause);
    }
}
