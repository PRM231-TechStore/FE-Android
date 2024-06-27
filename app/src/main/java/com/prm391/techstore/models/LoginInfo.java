package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {
    public LoginInfo(String userId, String token, String expiryDate) {
        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    private String userId;
    private String token;
    private String expiryDate;
}
