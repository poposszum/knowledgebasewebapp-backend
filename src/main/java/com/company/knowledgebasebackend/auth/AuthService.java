package com.company.knowledgebasebackend.auth;

import com.company.knowledgebasebackend.common.JwtAuthenticationResponse;
import com.company.knowledgebasebackend.user.UserEntity;
import com.company.knowledgebasebackend.user.UserService;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * Contains all the authentication services.
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private final String ERROR_MESSAGE = "Could not save user";

    /**
     * The following method is logging in the users.
     * Firstly checks if the credentials are correct, then generates the unique jwt token.
     */
    public JwtAuthenticationResponse signin(UserEntity loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail().toLowerCase(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Login failed");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * This method makes it possible to register new users.
     * It checks if the given email address is not registered yet, then builds a new user entity, and before
     * saving it to the database, checks if the just created user entity could be saved to the database without any trouble.
     */
    public void signup(UserEntity signUpRequest) throws AuthException {

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new AuthException("This Email Address is already in use");
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(Arrays.asList("USER"))
                .build();

        try {
            userService.save(userEntity);
        } catch (MongoException e) {
            throw new MongoException(ERROR_MESSAGE);
        }
    }

    /**
     * This method generates a password reset link, then sends it to the user in an email.
     */
    public StringBuilder generateKey(UserEntity userEntity, HttpServletRequest request) throws AuthException {
        final String GENERATE_KEY_ERROR = "An email was sent to your email address";

        UserEntity user = userService.findByEmail(userEntity.getEmail());

        if (user == null) {
           return new StringBuilder(GENERATE_KEY_ERROR);
        }

        PasswordResetKey passwordResetKey = new PasswordResetKey(UUID.randomUUID().toString());
        if (passwordResetKey.getResetKey() == null) {
            return new StringBuilder(GENERATE_KEY_ERROR);
        }
        user.setPasswordResetKey(passwordResetKey);

        try {
            userService.save(user);
        } catch (MongoException e) {
            throw new MongoException(ERROR_MESSAGE);
        }

        // this line is only for testing purposes, this will be changed soon!
        return new StringBuilder("http://localhost:8080/changepassword?resetKey=" + user.getPasswordResetKey().getResetKey());
    }

    /**
     * This method changes the password if the reset key is valid.
     */
    public void resetPassword(ChangePasswordRequest changePasswordRequest) throws AuthException {
        final String RESET_ERROR_MESSAGE = "This password reset link is expired or invalid";

        PasswordResetKey passwordResetKey = userService.findResetKey(changePasswordRequest.getResetKey());
        if (passwordResetKey == null || passwordResetKey.isExpired()) {
            throw new AuthException(RESET_ERROR_MESSAGE);
        }

        UserEntity user = userService.findUserByResetKey(changePasswordRequest.getResetKey());
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        user.setPasswordResetKey(null);

        try {
            userService.save(user);
        } catch (MongoException e) {
            throw new MongoException(ERROR_MESSAGE);
        }
    }
}

