package com.example.workersmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.workersmanager.models.WorkerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
TODO
Валидация форм
Кнопка добавления новых записей
Реализовать кнопки элемента списка
Реализовать пагинацию(свайпом перемещаться между страницами)
При неудачном запросе обновлять данные через некоторое время
***
Сделать поле информации со скроллом
Сортирование записей по определенному полю
Анимации во время сетевых запросов
Анимация списка элементов
Материал дизан
 */

public class MainActivity extends AppCompatActivity {
    static final String ACTIVITY_STATUS_CREATE = "ACTIVITY_STATUS_CREATE";
    static final String ACTIVITY_STATUS_UPDATE = "ACTIVITY_STATUS_UPDATE";
    static final String IS_AUTHORIZATION = "IS_AUTHORIZATION";
    static final int CALL_LOGIN_ACTIVITY = 0;
    static final int CALL_EDIT_ACTIVITY = 1;
    int PAGE_NUMBER = 1;
    final int COUNT_NOTE = 5;
    ListView listView;
    final Context context = this;
    WorkersAdapter workersAdapter;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv_workers);

        checkAuthorization();
    }

    public void getWorkers() {
        App.getWorkerApi().getWorkers(PAGE_NUMBER, COUNT_NOTE)
                .enqueue(new Callback<WorkerModel.GetWorkers>() {
            @Override
            public void onResponse(Call<WorkerModel.GetWorkers> call,
                                   Response<WorkerModel.GetWorkers> response) {
                fillListView(response.body().getWorkers());
            }

            @Override
            public void onFailure(Call<WorkerModel.GetWorkers> call, Throwable t) {
                Toast.makeText(context, "Loading error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteWorker(String id) {
        App.getWorkerApi().deleteWorker(id).enqueue(new Callback<WorkerModel>() {
            @Override
            public void onResponse(Call<WorkerModel> call, Response<WorkerModel> response) {
                getWorkers();
            }

            @Override
            public void onFailure(Call<WorkerModel> call, Throwable t) {
                Toast.makeText(context, "Deleting error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAuthorization() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String isAuthorization = sharedPreferences.getString(IS_AUTHORIZATION, "false");
        if(isAuthorization.equals("false")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, CALL_LOGIN_ACTIVITY);
        } else {
            getWorkers();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) return;
        switch (requestCode) {
            case CALL_LOGIN_ACTIVITY:
                SharedPreferences sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(IS_AUTHORIZATION, "true");
                ed.apply();
                break;
            case CALL_EDIT_ACTIVITY:
                workersAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void fillListView(List<WorkerModel> workers) {
        ArrayList<WorkerModel> workersList = new ArrayList<>(workers);
        for(WorkerModel w: workersList) w.visibility = View.GONE;
        workersAdapter = new WorkersAdapter(context, workersList, new AdditionalOperations());
        listView.setAdapter(workersAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkAuthorization();
    }

    class AdditionalOperations {
        public void updateWorker(WorkerModel worker) {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("worker", worker);
            intent.putExtra("status", ACTIVITY_STATUS_UPDATE);
            startActivityForResult(intent, CALL_EDIT_ACTIVITY);
        }

        public void deleteWorkerOperation(String id) {
            deleteWorker(id);
        }
    }
}