package com.prm391.techstore.clients;

import androidx.annotation.NonNull;

import com.prm391.techstore.BuildConfig;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TechStoreRetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        String techStoreUrl = BuildConfig.TECHSTORE_URL;

        retrofit = new Retrofit.Builder()
                .baseUrl(techStoreUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static Retrofit getClient(String userToken) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", userToken).build();
                return chain.proceed(request);
            }
        };
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(interceptor).build();
        String techStoreUrl = BuildConfig.TECHSTORE_URL;

        retrofit = new Retrofit.Builder()
                .baseUrl(techStoreUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
