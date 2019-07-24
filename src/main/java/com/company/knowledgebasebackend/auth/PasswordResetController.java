package com.company.knowledgebasebackend.auth;

import com.company.knowledgebasebackend.common.ApiResponse;
import com.company.knowledgebasebackend.user.UserEntity;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Handles the password reset requests.
 */

@Controller
@RequestMapping("/api/v1/forgot-password")
public class PasswordResetController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST, value = "/generatekey")
    public ResponseEntity<ApiResponse> generateKey(@Valid @RequestBody UserEntity passwordReset, HttpServletRequest request) throws AuthException {

        try {
            return new ResponseEntity<>(new ApiResponse(true, authService.generateKey(passwordReset, request).toString()), HttpStatus.OK);
        } catch (AuthException e) {
            throw new AuthException(e.getMessage());
        } catch (MongoException e) {
            throw new MongoException(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changepassword")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws AuthException {

        try {
            authService.resetPassword(changePasswordRequest);
        } catch (AuthException e) {
            throw new AuthException(e.getMessage());
        } catch (MongoException e) {
            throw new MongoException(e.getMessage());
        }
        return new ResponseEntity<>(new ApiResponse(true, "Your password was changed successfully"), HttpStatus.OK);
    }
}
