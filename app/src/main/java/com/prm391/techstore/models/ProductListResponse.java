package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ProductListResponse {
    @SerializedName("resultCode")
    public String resultCode;
    @SerializedName("resultMessage")
    public String resultMessage;
    @SerializedName("data")
    public ProductListDataObj data;

    public class ProductListDataObj{
        @SerializedName("items")
        public List<Product> items;
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
