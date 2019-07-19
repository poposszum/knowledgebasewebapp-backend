package com.company.knowledgebasebackend.auth;

import lombok.Data;

import java.util.Date;

@Data
public class PasswordResetKey {

    private String resetKey;
    private Date expiryDate;

    public PasswordResetKey(String resetKey) {
        this.resetKey = resetKey;
        Date now = new Date();
        this.expiryDate = new Date(now.getTime() + 360000);
    }

    public boolean isExpired(){
        return new Date().after(this.expiryDate);
    }
}
