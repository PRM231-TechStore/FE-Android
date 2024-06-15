package com.prm391.techstore.clients;

import com.prm391.techstore.BuildConfig;

import java.util.Map;

import okhttp3.OkHttpClient;
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
}
