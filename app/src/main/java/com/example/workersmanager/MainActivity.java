package com.example.workersmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.workersmanager.models.WorkerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
TODO
Валидация форм
Кнопка добавления новых записей
Реализовать кнопки элемента списка
***
Сортирование записей по определенному полю
Анимации во время сетевых запросов
Анимация списка элементов
 */

public class MainActivity extends AppCompatActivity {
    static final String ACTIVITY_STATUS_CREATE = "ACTIVITY_STATUS_CREATE";
    static final String ACTIVITY_STATUS_UPDATE = "ACTIVITY_STATUS_UPDATE";
    static final String IS_AUTHORIZATION = "IS_AUTHORIZATION";
    final int PAGE_NUMBER = 1;
    final int COUNT_NOTE = 5;
    ListView listView;
    final Context context = this;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv_workers);

        checkAuthorization();
    }

    private void getWorkers() {
        App.getWorkerApi().getWorkers(PAGE_NUMBER, COUNT_NOTE).enqueue(new Callback<WorkerModel.GetWorkers>() {
            @Override
            public void onResponse(Call<WorkerModel.GetWorkers> call, Response<WorkerModel.GetWorkers> response) {
                List<WorkerModel> workers = response.body().getWorkers();
                ArrayList<Map<String, Object>> data = new ArrayList<>(workers.size());
                for(int i=0;i<workers.size();i++) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("firstName", workers.get(i).getFirstName());
                    m.put("lastName", workers.get(i).getLastName());
                    data.add(m);
                }
                String[] from = {"firstName", "lastName"};
                int[] to = {R.id.tv_firstName, R.id.tv_lastName};

                SimpleAdapter simpleAdapter = new SimpleAdapter(context, data, R.layout.layout_worker_item,
                        from, to);
                listView.setAdapter(simpleAdapter);
            }

            @Override
            public void onFailure(Call<WorkerModel.GetWorkers> call, Throwable t) {
                Log.d("myLog", t.toString());
            }
        });
    }

    private void checkAuthorization() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String isAuthorization = sharedPreferences.getString("IS_AUTHORIZATION", "false");
        if(isAuthorization.equals("false")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
        } else {
            getWorkers();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            SharedPreferences sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("IS_AUTHORIZATION", "true");
            ed.commit();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        checkAuthorization();
    }
}