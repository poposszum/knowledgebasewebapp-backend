package com.company.knowledgebasebackend.auth;

import com.company.knowledgebasebackend.common.ApiResponse;
import com.company.knowledgebasebackend.common.JwtAuthenticationResponse;
import com.company.knowledgebasebackend.common.AuthException;
import com.company.knowledgebasebackend.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * This controller-class handles the requests to sign up and sign in users.
 */

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST, value = "/signin")
    public JwtAuthenticationResponse authenticateUser(@RequestBody UserEntity loginRequest) throws AuthException {

        JwtAuthenticationResponse jwtToken = authService.signin(loginRequest);

        try {
            if (jwtToken == null) {
                throw new AuthException("Login failed");
            }
        } catch (AuthException e) {
            throw new AuthException(e.getMessage());
        } catch (BadCredentialsException e){
            throw new BadCredentialsException(e.getMessage());
        }
        return jwtToken;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserEntity signUpRequest) throws AuthException {

        try {
            authService.signup(signUpRequest);
        } catch (AuthException e) {
            throw new AuthException(e.getMessage());
        }
        return new ResponseEntity<>(new ApiResponse(true, "Registration successful"), HttpStatus.OK);
    }


}
