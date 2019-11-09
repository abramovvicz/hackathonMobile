package com.harman.borsuki.carpooling.services;

import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.models.Passenger;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("find/all/")
    Call<List<BorsukiRoute>> getAllRoutes();

    @POST("request/insert/")
    Call<Passenger> addPassenger(@Body Passenger passenger);
}
