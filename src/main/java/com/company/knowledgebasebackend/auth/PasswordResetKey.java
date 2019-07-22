package com.company.knowledgebasebackend.auth;

import lombok.Data;

import java.util.Date;

/**
 * This class contains the password reset key entities.
 */

@Data
public class PasswordResetKey {

    private String resetKey;
    private Date expiryDate;

    public PasswordResetKey(String resetKey) {
        this.resetKey = resetKey;
        Date now = new Date();
        this.expiryDate = new Date(now.getTime() + 600 * 1000); // first part (600) means the seconds (10 minutes), last part (1000) is the transformation to milliseconds
    }

    public boolean isExpired(){
        return new Date().after(this.expiryDate);
    }
}
