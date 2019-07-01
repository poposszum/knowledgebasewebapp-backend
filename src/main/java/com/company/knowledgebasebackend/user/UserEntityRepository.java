package com.company.knowledgebasebackend.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUsername(String username);
}
