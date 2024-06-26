package com.prm391.techstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProduct extends Product{
    private int amount;
}
