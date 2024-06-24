package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
//    "userId": "",
//            "token": "sosoadhoaljdsa",
//            "expiryDate": ""

    private String userId;
    private String token;
    private String expiryDate;
}
