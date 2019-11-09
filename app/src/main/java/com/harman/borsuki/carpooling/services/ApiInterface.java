package com.harman.borsuki.carpooling.services;

import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.models.Passenger;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("find/all/")
    Call<List<BorsukiRoute>> getAllRoutes();

    @POST("request/insert/")
    Call<Passenger> addPassenger(@Body Passenger passenger);

    @POST("insert")
    Call<BorsukiRoute> addRoute(@Body BorsukiRoute borsukiRoute);

    @GET("request/find")
    Call<Passenger> getPassanger(@Query("driverId") String driverId);
}
