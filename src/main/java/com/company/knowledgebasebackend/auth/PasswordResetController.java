package com.company.knowledgebasebackend.auth;

import com.company.knowledgebasebackend.common.ApiResponse;
import com.company.knowledgebasebackend.common.AuthException;
import com.company.knowledgebasebackend.user.UserEntity;
import com.company.knowledgebasebackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/forgot-password")
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.POST, value = "/generatekey")
    public ResponseEntity<ApiResponse> generateKey(@RequestBody UserEntity userEntity, HttpServletRequest request) throws AuthException {

        try {
            return new ResponseEntity<>(new ApiResponse(true, authService.generateKey(userEntity, request).toString()), HttpStatus.BAD_REQUEST);
        } catch (AuthException ex) {
            throw new AuthException(ex.getMessage());
        }


    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody UserEntity userEntity, @RequestParam("resetKey") String resetKey) {

        PasswordResetKey passwordResetKey = userService.findResetKey(resetKey);

        if (passwordResetKey == null || passwordResetKey.isExpired()) {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid password reset key."), HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userService.findUserByResetKey(resetKey);

        user.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userService.save(user);

        return new ResponseEntity<>(new ApiResponse(true, "Password reset successful"), HttpStatus.OK);
    }
}
