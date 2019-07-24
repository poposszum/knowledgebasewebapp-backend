package com.company.knowledgebasebackend.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Removes all the articles from the database.
 */
@Service
public class ArticleDbFiller implements CommandLineRunner {
    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        articleService.deleteAll();
    }
}
