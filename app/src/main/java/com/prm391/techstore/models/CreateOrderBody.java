package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateOrderBody {
    private CreateOrderPayload data;

    public CreateOrderBody(CreateOrderPayload data) {
        this.data = data;
    }
}
