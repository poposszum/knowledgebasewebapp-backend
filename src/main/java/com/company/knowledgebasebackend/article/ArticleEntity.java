package com.company.knowledgebasebackend.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "Articles")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ArticleEntity {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private List<ObjectId> authors = new ArrayList<>();
    private Date dateCreated = new Date();
    private Date dateModified = new Date();
}
