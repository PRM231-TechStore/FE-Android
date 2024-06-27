package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class UserDetailsResponse {
    @SerializedName("resultCode")
    private String resultCode;
    @SerializedName("resultMessage")
    private String resultMessage;
    @SerializedName("data")
    private UserDetailsResponse.UserDetails data;

    @Getter
    public class UserDetails{
        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("email")
        private String email;
        @SerializedName("phone")
        private String phone;
        @SerializedName("address")
        private String address;
    }
}
