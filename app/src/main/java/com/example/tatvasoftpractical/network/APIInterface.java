package com.example.tatvasoftpractical.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/users?")
    Call<JsonObject> getetUserData( @Query("offset") String offset,
                                    @Query("limit") String limit);

}