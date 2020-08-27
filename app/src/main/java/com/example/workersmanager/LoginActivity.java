package com.example.workersmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.workersmanager.models.UserModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    EditText etLogin;
    EditText etPassword;
    TextInputLayout ilLogin;
    TextInputLayout ilPassword;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
        ilLogin = findViewById(R.id.il_login);
        ilPassword = findViewById(R.id.il_password);

        etLogin.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
    }

    public void clickEnter(View view) {
        if(!allVerification()) return;
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        UserModel loginModel = new UserModel(login, password);
        App.getUserApi().loginUser(loginModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()) applyAuthorization();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clickRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) applyAuthorization();
    }

    public void applyAuthorization() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_login:
                if(!hasFocus) {
                    MainActivity.verification(ilLogin, etLogin);
                } else ilLogin.setError("");
                break;
            case R.id.et_password:
                if(!hasFocus) {
                    MainActivity.verification(ilPassword, etPassword);
                } else ilPassword.setError("");
                break;
        }
    }

    public boolean allVerification() {
        boolean flag = true;
        if(!MainActivity.verification(ilLogin, etLogin)) flag = false;
        if(!MainActivity.verification(ilPassword, etPassword) && flag) flag = false;
        return flag;
    }
}