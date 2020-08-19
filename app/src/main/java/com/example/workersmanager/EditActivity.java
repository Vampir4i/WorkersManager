package com.example.workersmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.workersmanager.models.WorkerModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
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
}