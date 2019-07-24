package com.company.knowledgebasebackend.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Contains the password reset entity from the request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @Size(min = 8, message = "Password size must be between 3 and 64 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$]).{8,}$",
            message = "Password shall contain alphabetic characters, numbers and at least one of the following characters: !@#$. " +
                    "Password can not start or end with space.")
    private String password;
    private String resetKey;
}
