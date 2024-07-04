package com.prm391.techstore.utils;


import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.models.ErrorResponse;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ErrorMessageUtils {
    public static String getErrorMessage(Response response){

        Retrofit retrofit = TechStoreRetrofitClient.getClient();

        try{
            Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);

            ErrorResponse error = errorConverter.convert(response.errorBody());

            return error.resultMessage;
        }catch (Exception e){
            return "Something went wrong !";
        }
    }
}
