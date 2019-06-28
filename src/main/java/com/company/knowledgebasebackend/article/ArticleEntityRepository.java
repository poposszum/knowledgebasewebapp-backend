package com.company.knowledgebasebackend.article;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleEntityRepository extends MongoRepository<ArticleEntity, ObjectId> {
}
