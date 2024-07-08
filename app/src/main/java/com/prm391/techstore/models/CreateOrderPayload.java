package com.prm391.techstore.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderPayload {
    private String user_id;
    private long amount;
    private List<RequestProductObj> products;

    public CreateOrderPayload(String user_id, long amount, List<RequestProductObj> products) {
        this.user_id = user_id;
        this.amount = amount;
        this.products = products;
    }
}

