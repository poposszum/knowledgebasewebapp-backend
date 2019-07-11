package com.company.knowledgebasebackend.payload;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 2, max = 64)
    @Pattern(regexp = "^[A-Za-z]+", message = "First name can only contain alphabetical characters.")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 64)
    @Pattern(regexp = "^[A-Za-z]+", message = "Last name can only contain alphabetical characters.")
    private String lastName;

    @NotBlank
    @Indexed(unique = true)
    @Size(min = 3, max = 64)
    @Pattern(regexp = "^[a-z0-9\\-_]+@[a-z0-9]+\\.\\w{2,3}$", message = "This is not a valid Email Address.")
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$]).{8,}$", message = "Password shall contain at least one of these characters: !@#$")
    private String password;
}
