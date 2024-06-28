package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("resultCode")
    public String resultCode;
    @SerializedName("resultMessage")
    public String resultMessage;
    @SerializedName("data")
    public LoginInfo  loginInfo;
}
