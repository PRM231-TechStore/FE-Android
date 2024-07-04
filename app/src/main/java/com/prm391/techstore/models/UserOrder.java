package com.prm391.techstore.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserOrder {
    private String orderId;
    private int amount;
    private int status;
    private String userId;
    private LocalDate createdAt;
}
