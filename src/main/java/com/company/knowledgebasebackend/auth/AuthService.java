package com.company.knowledgebasebackend.auth;

import com.company.knowledgebasebackend.common.AuthException;
import com.company.knowledgebasebackend.common.JwtAuthenticationResponse;
import com.company.knowledgebasebackend.user.UserEntity;
import com.company.knowledgebasebackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.UUID;

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

    /**
     * The following method is logging in the users.
     * Firstly checks if the credentials are okay, then generates the unique jwt token.
     */

    public JwtAuthenticationResponse signin(@Valid @RequestBody UserEntity loginRequest) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail().toLowerCase(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Login Failed");
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

    public void signup(@Valid @RequestBody UserEntity signUpRequest) throws AuthException {

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new AuthException("This Email Address is already in use.");
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(Arrays.asList("USER"))
                .build();

        if (userService.save(userEntity) == null)
            throw new AuthException("Could not save user.");

        userService.save(userEntity);
    }

    /**
     * This method generates a password reset link, then sends it to the user in an email.
     */

    public StringBuilder generateKey(@RequestBody UserEntity userEntity, HttpServletRequest request) throws AuthException {

        String errorMsg = "Could not change password";

        UserEntity user = userService.findByEmail(userEntity.getEmail());

        if (user == null) {
            throw new AuthException(errorMsg);
        }

        PasswordResetKey passwordResetKey = new PasswordResetKey(UUID.randomUUID().toString());

        if (passwordResetKey.getResetKey() == null) {
            throw new AuthException(errorMsg);
        }

        user.setPasswordResetKey(passwordResetKey);

        if (userService.save(user) == null || user.getPasswordResetKey().getResetKey() == null) {
            throw new AuthException(errorMsg);
        }

        userService.save(user);

        StringBuilder URL = new StringBuilder(request.getRequestURL().toString() + "?resetKey=" + user.getPasswordResetKey().getResetKey());

        return URL;

    }
}
