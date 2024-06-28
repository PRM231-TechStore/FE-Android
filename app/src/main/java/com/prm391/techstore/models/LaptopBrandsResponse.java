package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class LaptopBrandsResponse {
    @SerializedName("resultCode")
    private String resultCode;
    @SerializedName("resultMessage")
    private String resultMessage;
    @SerializedName("data")
    private LaptopBrandsResponse.Data data;

    @Getter
    public class Data{
        @SerializedName("items")
        private List<LaptopBrand> items;
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
        @Getter
        public class LaptopBrand{
            @SerializedName("name")
            private String name;
            @SerializedName("image")
            private String image;
        }
    }
}
