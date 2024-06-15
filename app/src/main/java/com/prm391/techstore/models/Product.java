package com.prm391.techstore.models;

import android.graphics.Bitmap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private String image;
    private String description;
    private Double price;
}