package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class UserOrdersResponse {
    @SerializedName("resultCode")
    private String resultCode;
    @SerializedName("resultMessage")
    private String resultMessage;
    @SerializedName("data")
    private UserOrdersResponse.OrderListDataObj data;

    @Getter
    public class OrderListDataObj{
        @SerializedName("items")
        private List<UserOrder> items;
        @SerializedName("totalCount")
        private Integer totalCount;
        @SerializedName("hasNextPage")
        private Boolean hasNextPage;
        @SerializedName("hasPrevPage")
        private Boolean hasPrevPage;
        @SerializedName("pageNumber")
        private Integer pageNumber;
        @SerializedName("pageSize")
        private Integer pageSize;
    }
}
