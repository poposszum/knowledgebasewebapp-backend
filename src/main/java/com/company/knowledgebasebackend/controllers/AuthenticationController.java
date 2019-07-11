package com.company.knowledgebasebackend.controllers;

import com.company.knowledgebasebackend.payload.LoginRequest;
import com.company.knowledgebasebackend.payload.SignUpRequest;
import com.company.knowledgebasebackend.services.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method = RequestMethod.POST, value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return serviceLayer.signin(loginRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){

        return serviceLayer.signup(signUpRequest);
    }



}
