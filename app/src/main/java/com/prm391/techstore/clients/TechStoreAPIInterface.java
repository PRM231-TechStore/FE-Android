package com.prm391.techstore.clients;

import com.prm391.techstore.models.ProductByIdResponse;
import com.prm391.techstore.models.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TechStoreAPIInterface {
    @GET(TechStoreAPIEndpoints.GET_ALL_PRODUCTS)
    Call<ProductListResponse> getProducts();

    @GET(TechStoreAPIEndpoints.GET_PRODUCT_BY_ID)
    Call<ProductByIdResponse> getProductById(@Path("productId") String productId);
}
