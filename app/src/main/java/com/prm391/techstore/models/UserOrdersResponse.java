package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserOrdersResponse {
    @SerializedName("resultCode")
    public String resultCode;
    @SerializedName("resultMessage")
    public String resultMessage;
    @SerializedName("data")
    public UserOrdersResponse.OrderListDataObj data;

    public class OrderListDataObj{
        @SerializedName("items")
        public List<UserOrder> items;
        @SerializedName("totalCount")
        public Integer totalCount;
        @SerializedName("hasNextPage")
        public Boolean hasNextPage;
        @SerializedName("hasPrevPage")
        public Boolean hasPrevPage;
        @SerializedName("pageNumber")
        public Integer pageNumber;
        @SerializedName("pageSize")
        public Integer pageSize;
    }
}
