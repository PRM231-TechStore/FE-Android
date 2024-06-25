package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPayLoad {

    private String username;

    private String password;

    public LoginPayLoad(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
