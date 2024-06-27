package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPayload {
    private String username;
    private String password;
    private String email;

    public RegisterPayload(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
