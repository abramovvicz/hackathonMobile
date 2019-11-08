package com.harman.borsuki.carpooling.services;

import com.harman.borsuki.carpooling.models.BorsukiRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("find/all/")
    Call<List<BorsukiRoute>> getAllRoutes();
}
