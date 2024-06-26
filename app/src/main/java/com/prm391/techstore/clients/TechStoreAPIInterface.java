package com.prm391.techstore.clients;

import com.prm391.techstore.models.LoginRequestBody;
import com.prm391.techstore.models.LoginResponse;
import com.prm391.techstore.models.ProductByIdResponse;
import com.prm391.techstore.models.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TechStoreAPIInterface {
    @GET(TechStoreAPIEndpoints.GET_ALL_PRODUCTS)
    Call<ProductListResponse> getProducts(@Query("searchTerm") String searchTerm,
                                          @Query("sortBy") String sortBy,
                                          @Query("sortOrder") String sortOrder,
                                          @Query("minPrice") String minPrice,
                                          @Query("maxPrice") String maxPrice,
                                          @Query("pageNumber") String pageNumber,
                                          @Query("pageSize") String pageSize);

    @GET(TechStoreAPIEndpoints.GET_PRODUCT_BY_ID)
    Call<ProductByIdResponse> getProductById(@Path("productId") String productId);

    @POST(TechStoreAPIEndpoints.LOGIN)
    Call<LoginResponse> login(@Body LoginRequestBody data);

    @POST(TechStoreAPIEndpoints.REGISTER)
    Call Register();
}
