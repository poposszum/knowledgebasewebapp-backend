package com.company.knowledgebasebackend.article;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<ArticleEntity, ObjectId> {
    ArticleEntity findArticleById(ObjectId id);
    List<ArticleEntity> findAll();
}
