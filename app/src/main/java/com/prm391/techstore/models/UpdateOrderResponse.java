package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class UpdateOrderResponse {
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultMessage")
    private String resultMessage;
}
