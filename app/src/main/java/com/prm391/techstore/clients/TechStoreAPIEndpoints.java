package com.prm391.techstore.clients;

public class TechStoreAPIEndpoints {
    public static final String GET_ALL_PRODUCTS = "/products?";
    public static final String GET_PRODUCT_BY_ID = "/products/{productId}";

    public static final String LOGIN = "/login";

    public static final String REGISTER = "/register";
    public static final String GET_USER_DETAILS = "/users/{userId}";
    public static final String GET_LAPTOP_BRANDS = "/labels?";
    public static final String GET_ORDERS_BY_USER_ID = "/orders?";
    public static final String GET_ORDER_DETAILS_BY_ORDER_ID = "/orders/{orderId}";
    public static final String CREATE_ORDER = "/orders";
    public static final String UPDATE_ORDER = "/orders/{orderId}";
}
