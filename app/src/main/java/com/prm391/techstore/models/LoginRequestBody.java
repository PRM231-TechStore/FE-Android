package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestBody {
    private LoginPayLoad data;

    public LoginRequestBody(LoginPayLoad data) {
        this.data = data;
    }
}
