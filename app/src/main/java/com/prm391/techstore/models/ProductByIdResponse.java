package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

public class ProductByIdResponse {
    @SerializedName("resultCode")
    public String resultCode;
    @SerializedName("resultMessage")
    public String resultMessage;
    @SerializedName("data")
    public Product product;
}
