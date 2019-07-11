package com.company.knowledgebasebackend.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Email Address is required.")
    private String email;
    @NotBlank(message = "Password is required.")
    private String password;

    public LoginRequest(@NotBlank String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }
}
