package com.prm391.techstore.clients;

import com.prm391.techstore.models.CreateOrderBody;
import com.prm391.techstore.models.CreateOrderResponse;
import com.prm391.techstore.models.LaptopBrandsResponse;
import com.prm391.techstore.models.LoginRequestBody;
import com.prm391.techstore.models.LoginResponse;
import com.prm391.techstore.models.ProductByIdResponse;
import com.prm391.techstore.models.ProductListResponse;
import com.prm391.techstore.models.RegisterBody;
import com.prm391.techstore.models.UpdateOrderBody;
import com.prm391.techstore.models.UpdateOrderResponse;
import com.prm391.techstore.models.UserDetailsResponse;
import com.prm391.techstore.models.UserOrderDetailsResponse;
import com.prm391.techstore.models.UserOrdersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
                                          @Query("pageSize") String pageSize,
                                          @Query("label") String label);

    @GET(TechStoreAPIEndpoints.GET_PRODUCT_BY_ID)
    Call<ProductByIdResponse> getProductById(@Path("productId") String productId);

    @POST(TechStoreAPIEndpoints.LOGIN)
    Call<LoginResponse> login(@Body LoginRequestBody data);

    @POST(TechStoreAPIEndpoints.REGISTER)
    Call<Void> Register(@Body RegisterBody data);

    @GET(TechStoreAPIEndpoints.GET_USER_DETAILS)
    Call<UserDetailsResponse> getUserDetailsById(@Header("Authorization") String token, @Path("userId") String userId);

    @GET(TechStoreAPIEndpoints.GET_LAPTOP_BRANDS)
    Call<LaptopBrandsResponse> getLaptopBrands(@Query("pageNumber") String pageNumber, @Query("pageSize") String pageSize);

    @POST(TechStoreAPIEndpoints.CREATE_ORDER)
    Call<CreateOrderResponse> createOrder(@Body CreateOrderBody data);

    @PUT(TechStoreAPIEndpoints.UPDATE_ORDER)
    Call<UpdateOrderResponse> updateOrder(@Path("orderId") String orderId, @Body UpdateOrderBody data);

    @GET(TechStoreAPIEndpoints.GET_ORDERS_BY_USER_ID)
    Call<UserOrdersResponse> getOrdersByUserId(@Header("Authorization") String token,
                                               @Query("userId") String userId,
                                               @Query("pageNumber") String pageNumber,
                                               @Query("pageSize") String pageSize);

    @GET(TechStoreAPIEndpoints.GET_ORDER_DETAILS_BY_ORDER_ID)
    Call<UserOrderDetailsResponse> getOrderDetailsByOrderId(@Header("Authorization") String token,
                                                            @Path("orderId") String orderId);

}
