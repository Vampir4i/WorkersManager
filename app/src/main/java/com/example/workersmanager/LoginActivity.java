package com.example.workersmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.workersmanager.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etLogin;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
    }

    public void clickEnter(View view) {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        UserModel loginModel = new UserModel(login, password);
        App.getUserApi().loginUser(loginModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel userModel = response.body();
                Log.d("myLog", userModel.getLogin());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("myLog", t.toString());
            }
        });

    }
}