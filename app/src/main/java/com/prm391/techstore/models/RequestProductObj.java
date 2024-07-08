package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProductObj {
    private String id;
    private int quantity;

    public RequestProductObj (String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
