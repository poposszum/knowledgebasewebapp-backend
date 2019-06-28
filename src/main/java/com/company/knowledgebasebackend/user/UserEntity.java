package com.company.knowledgebasebackend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "Users")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {

    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    @Size(min = 5, max = 15)
    private String username;
    private String password;
    private List<ObjectId> articles = new ArrayList<>();
    private Date registrationDate = new Date();
}
