package com.malkollm.foroperators;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText etStationNumber, etStationFrequency, etStationCurrent,
            etStationLoading, etStationZsp, etStationTemperature,
            etStationPressure, etStationProgramStart, etStationProgramEnd,
            etStationIsolation;
    Button btnSaveStationParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etStationNumber = findViewById(R.id.etStationNumber);
        etStationFrequency = findViewById(R.id.etStationFrequency);
        etStationCurrent = findViewById(R.id.etStationCurrent);
        etStationLoading = findViewById(R.id.etStationLoading);
        etStationZsp = findViewById(R.id.etStationZsp);
        etStationTemperature = findViewById(R.id.etStationTemperature);
        etStationPressure = findViewById(R.id.etStationPressure);
        etStationProgramStart = findViewById(R.id.etStationProgramStart);
        etStationProgramEnd = findViewById(R.id.etStationProgramEnd);
        etStationIsolation = findViewById(R.id.etStationIsolation);
        btnSaveStationParams = findViewById(R.id.btnSaveStationParams);

        btnSaveStationParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addStationParams(
                        Integer.parseInt(etStationNumber.getText().toString().trim()),
                        Float.parseFloat(etStationFrequency.getText().toString().trim()),
                        Float.parseFloat(etStationCurrent.getText().toString().trim()),
                        Float.parseFloat(etStationLoading.getText().toString().trim()),
                        Float.parseFloat(etStationZsp.getText().toString().trim()),
                        Float.parseFloat(etStationTemperature.getText().toString().trim()),
                        Float.parseFloat(etStationPressure.getText().toString().trim()),
                        Float.parseFloat(etStationProgramStart.getText().toString().trim()),
                        Float.parseFloat(etStationProgramEnd.getText().toString().trim()),
                        Float.parseFloat(etStationIsolation.getText().toString().trim()));

                //TODO вынести в класс Utils
                //Refresh Activity
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}