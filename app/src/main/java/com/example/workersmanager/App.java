package com.example.workersmanager;

import android.app.Application;

import com.example.workersmanager.api.UserApi;
import com.example.workersmanager.api.WorkerApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static UserApi userApi;
    private static WorkerApi workerApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
         retrofit = new Retrofit.Builder()
                 .baseUrl("http://192.168.1.7:8080/api/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         userApi = retrofit.create(UserApi.class);
         workerApi = retrofit.create(WorkerApi.class);
    }

    public static UserApi getUserApi(){
        return userApi;
    }

    public static WorkerApi getWorkerApi(){
        return workerApi;
    }
}
