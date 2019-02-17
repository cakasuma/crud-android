package com.example.mustofaamami.testfakeapi.api;

import com.example.mustofaamami.testfakeapi.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiRequestBiodata {
    @POST("person")
    Call<ResponseModel> sendBiodata(@Body ResponseModel body);

    @GET("person")
    Call<List<ResponseModel>> getBiodata();

    @PUT("person")
    Call<ResponseModel> updateBiodata(@Query("email") String email,
                                      @Body ResponseModel body);

    @DELETE("person")
    Call<ResponseModel> deleteBiodata(@Query("email") String email);
}
