package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class UserOrderDetailsResponse {
    @SerializedName("resultCode")
    private String resultCode;
    @SerializedName("resultMessage")
    private String resultMessage;
    @SerializedName("data")
    private UserOrderDetailsResponse.Data data;
    @Getter
    public class Data{
        @SerializedName("id")
        public String id;
        @SerializedName("amount")
        public int amount;
        @SerializedName("status")
        public int status;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("orderDetails")
        public List<UserOrderDetails> orderDetails;
        @Getter
        public class UserOrderDetails{
            @SerializedName("orderId")
            public String orderId;
            @SerializedName("productId")
            public String productId;
            @SerializedName("quantity")
            public int quantity;
        }
    }
}
