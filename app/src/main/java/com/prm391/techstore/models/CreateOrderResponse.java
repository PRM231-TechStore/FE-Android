package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class CreateOrderResponse {
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultMessage")
    private String resultMessage;
    @SerializedName("data")
    private Data data;

    @Getter
    public class Data {
        @SerializedName("orderId")
        private String orderId;
    }
}
