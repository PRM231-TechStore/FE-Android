package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateOrderBody {
    private UpdateOrderPayload data;

    public UpdateOrderBody(UpdateOrderPayload data) {
        this.data = data;
    }
}
