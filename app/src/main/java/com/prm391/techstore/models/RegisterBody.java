package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterBody {
    private RegisterPayload data;

    public RegisterBody(RegisterPayload data) {
        this.data = data;
    }
}
