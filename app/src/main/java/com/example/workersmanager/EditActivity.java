package com.example.workersmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.workersmanager.models.WorkerModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnFocusChangeListener {
    private String status;
    String _id;
    EditText etFirstName;
    EditText etLastName;
    EditText etPosition;
    EditText etAge;
    EditText etGender;
    EditText etSalary;
    EditText etData;
    EditText etInfo;

    TextInputLayout ilFirstName;
    TextInputLayout ilLastName;
    TextInputLayout ilPosition;
    TextInputLayout ilAge;
    TextInputLayout ilGender;
    TextInputLayout ilSalary;
    TextInputLayout ilData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        status = intent.getStringExtra("status");

        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etPosition = findViewById(R.id.et_position);
        etAge = findViewById(R.id.et_age);
        etGender = findViewById(R.id.et_gender);
        etSalary = findViewById(R.id.et_salary);
        etData = findViewById(R.id.et_data);
        etInfo = findViewById(R.id.et_info);

        ilFirstName = findViewById(R.id.il_firstName);
        ilLastName = findViewById(R.id.il_lastName);
        ilPosition = findViewById(R.id.il_position);
        ilAge = findViewById(R.id.il_age);
        ilGender = findViewById(R.id.il_gender);
        ilSalary = findViewById(R.id.il_salary);
        ilData = findViewById(R.id.il_data);

        etFirstName.setOnFocusChangeListener(this);
        etLastName.setOnFocusChangeListener(this);
        etPosition.setOnFocusChangeListener(this);
        etAge.setOnFocusChangeListener(this);
        etGender.setOnFocusChangeListener(this);
        etSalary.setOnFocusChangeListener(this);
        etData.setOnFocusChangeListener(this);

        etData.setOnClickListener(this);

        if(status.equals(MainActivity.ACTIVITY_STATUS_UPDATE)) {
            WorkerModel worker = (WorkerModel) intent.getSerializableExtra("worker");
            _id = worker.get_id();
            etFirstName.setText(worker.getFirstName());
            etLastName.setText(worker.getLastName());
            etPosition.setText(worker.getPosition());
            etAge.setText("" + worker.getAge());
            etGender.setText(worker.getGender());
            etSalary.setText("" + worker.getSalary());
            etData.setText(worker.getData());
            etInfo.setText(worker.getInfo());
        }
    }

    public void clickButtons(View view) {
        if(view.getId() == R.id.btn_cancel) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        if(!allVerification()) return;
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String position = etPosition.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        String gender = etGender.getText().toString();
        int salary = Integer.parseInt(etSalary.getText().toString());
        String data = etData.getText().toString();
        String info = etInfo.getText().toString();
        WorkerModel workerModel = new WorkerModel(firstName, lastName, age, gender, info,
                data, salary, position);
        if(status.equals(MainActivity.ACTIVITY_STATUS_CREATE)) {
            addWorker(workerModel);
        } else if(status.equals(MainActivity.ACTIVITY_STATUS_UPDATE)) {
            workerModel.set_id(_id);
            updateWorker(workerModel);
        }
    }

    private void addWorker(WorkerModel workerModel) {
        App.getWorkerApi().addWorker(workerModel).enqueue(new Callback<WorkerModel>() {
            @Override
            public void onResponse(Call<WorkerModel> call, Response<WorkerModel> response) {
                Toast.makeText(EditActivity.this, "Insert successful",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<WorkerModel> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Insert fail",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void updateWorker(WorkerModel workerModel) {
        App.getWorkerApi().updateWorker(workerModel).enqueue(new Callback<WorkerModel>() {
            @Override
            public void onResponse(Call<WorkerModel> call, Response<WorkerModel> response) {
                Toast.makeText(EditActivity.this, "Update successful",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<WorkerModel> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Update fail",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etData.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_firstName:
                if(!hasFocus) {
                    MainActivity.verification(ilFirstName, etFirstName);
                } else ilFirstName.setError("");
                break;
            case R.id.et_lastName:
                if(!hasFocus) {
                    MainActivity.verification(ilLastName, etLastName);
                } else ilLastName.setError("");
                break;
            case R.id.et_position:
                if(!hasFocus) {
                    MainActivity.isEmptyField(ilPosition, etPosition);
                } else ilPosition.setError("");
                break;
            case R.id.et_age:
                if(!hasFocus) {
                    MainActivity.isEmptyField(ilAge, etAge);
                } else ilAge.setError("");
                break;
            case R.id.et_gender:
                if(!hasFocus) {
                    MainActivity.isEmptyField(ilGender, etGender);
                } else ilGender.setError("");
                break;
            case R.id.et_salary:
                if(!hasFocus) {
                    MainActivity.isEmptyField(ilSalary, etSalary);
                } else ilSalary.setError("");
                break;
            case R.id.et_data:
                if(!hasFocus) {
                    MainActivity.isEmptyField(ilData, etData);
                } else ilData.setError("");
                break;
        }
    }

    public boolean allVerification() {
        boolean flag = true;
        if(!MainActivity.verification(ilFirstName, etFirstName)) flag = false;
        if(!MainActivity.verification(ilLastName, etLastName) && flag) flag = false;
        if(MainActivity.isEmptyField(ilPosition, etPosition) && flag) flag = false;
        if(MainActivity.isEmptyField(ilAge, etAge) && flag) flag = false;
        if(MainActivity.isEmptyField(ilGender, etGender) && flag) flag = false;
        if(MainActivity.isEmptyField(ilSalary, etSalary) && flag) flag = false;
        if(MainActivity.isEmptyField(ilData, etData) && flag) flag = false;
        return flag;
    }
}