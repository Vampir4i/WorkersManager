package com.example.workersmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.workersmanager.models.UserModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    EditText etLogin;
    EditText etPassword;
    EditText etEmail;
    TextInputLayout ilLogin;
    TextInputLayout ilPassword;
    TextInputLayout ilEmail;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_email);
        ilLogin = findViewById(R.id.il_login);
        ilPassword = findViewById(R.id.il_password);
        ilEmail = findViewById(R.id.il_email);

        etLogin.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
        etEmail.setOnFocusChangeListener(this);
    }

    public void clickEnter(View view) {
        if(!allVerification()) return;
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        UserModel userModel = new UserModel(login, password, email);
        App.getUserApi().registerUser(userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });
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
            case R.id.et_email:
                if(!hasFocus) {
                    MainActivity.verification(ilEmail, etEmail);
                } else ilEmail.setError("");
                break;
        }
    }

    public boolean allVerification() {
        boolean flag = true;
        if(!MainActivity.verification(ilLogin, etLogin)) flag = false;
        if(!MainActivity.verification(ilPassword, etPassword) && flag) flag = false;
        if(!MainActivity.verification(ilEmail, etEmail) && flag) flag = false;
        return flag;
    }
}