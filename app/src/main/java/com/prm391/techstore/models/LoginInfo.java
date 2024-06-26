package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {
    private String userId;
    private String token;
    private String expiryDate;
}
