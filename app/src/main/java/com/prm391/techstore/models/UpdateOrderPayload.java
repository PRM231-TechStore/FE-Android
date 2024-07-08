package com.prm391.techstore.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderPayload {
    private int status;

    public UpdateOrderPayload(int status) {
        this.status = status;
    }
}
