package com.company.knowledgebasebackend.user;

import com.company.knowledgebasebackend.auth.PasswordResetKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the user entities.
 */

@Document(collection = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements Serializable {

    @Id
    private ObjectId id;
    @Size(min = 2, max = 64, message = "First Name size must be between 3 and 64 characters.")
    @Pattern(regexp = "^[A-Za-z]+",
            message = "First name can only contain alphabetical characters.")
    private String firstName;
    @Size(min = 2, max = 64, message = "Last Name size must be between 3 and 64 characters.")
    @Pattern(regexp = "^[A-Za-z]+",
            message = "Last name can only contain alphabetical characters.")
    private String lastName;
    @Indexed(unique = true)
    @Size(min = 3, max = 64, message = "Email Address size must be between 3 and 64 characters.")
    @Pattern(regexp = "^[a-z0-9\\-_]+@[a-z0-9]+\\.\\w{2,3}$",
            message = "Please enter a valid Email Address.")
    private String email;
    @Size(min = 8, message = "Password size must be between 3 and 64 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$]).{8,}$",
            message = "Password shall contain alphabetic characters, numbers and at least one of the following characters: !@#$. " +
                    "Password can not start or end with space.")
    private String password;
    private List<String> roles = new ArrayList<>();
    private PasswordResetKey passwordResetKey;
}
