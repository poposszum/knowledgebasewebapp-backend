package com.company.knowledgebasebackend.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 60)
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 60)
    private String lastName;

    @NotBlank
    @Size(max = 40)
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
