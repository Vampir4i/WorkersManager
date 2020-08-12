package com.example.workersmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.workersmanager.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText etLogin;
    EditText etPassword;
    EditText etEmail;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_email);
    }

    public void clickEnter(View view) {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        UserModel userModel = new UserModel(login, password, email);
        App.getUserApi().registerUser(userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}