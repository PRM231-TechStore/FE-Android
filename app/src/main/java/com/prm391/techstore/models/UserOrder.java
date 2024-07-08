package com.prm391.techstore.models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserOrder {
    @SerializedName("id")
    private String id;
    @SerializedName("amount")
    private int amount;
    @SerializedName("status")
    private int status;
    @SerializedName("userId")
    private String userId;
    @SerializedName("createdAt")
    private String createdAt;
}
