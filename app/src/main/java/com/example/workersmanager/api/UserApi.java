package com.example.workersmanager.api;

import com.example.workersmanager.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("login")
    Call<UserModel> loginUser(@Body UserModel user);

    @POST("register")
    Call<UserModel> registerUser(@Body UserModel user);
}
