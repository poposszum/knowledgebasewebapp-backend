package com.company.knowledgebasebackend.login;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(){
        return "login";
    }
}
