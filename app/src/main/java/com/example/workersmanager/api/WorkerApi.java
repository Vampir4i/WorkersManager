package com.example.workersmanager.api;

import com.example.workersmanager.models.WorkerModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkerApi {
    @GET("workers/")
    Call<WorkerModel.GetWorkers> getWorkers(@Query("page") int page, @Query("count") int count);

    @POST("workers/")
    Call<WorkerModel> addWorker(@Body WorkerModel worker);

    @PUT("workers/")
    Call<WorkerModel> updateWorker(@Body WorkerModel worker);

    @DELETE("workers/{id}")
    Call<WorkerModel> deleteWorker(@Path("id") int id);
}
