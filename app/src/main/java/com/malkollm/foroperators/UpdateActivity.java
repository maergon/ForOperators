package com.malkollm.foroperators;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText etStationNumberUpdate, etStationFrequencyUpdate, etStationCurrentUpdate,
            etStationLoadingUpdate, etStationZspUpdate, etStationTemperatureUpdate,
            etStationPressureUpdate, etStationProgramStartUpdate, etStationProgramEndUpdate,
            etStationIsolationUpdate, etKP;

    String id, number, frequency, current, loading, zsp, temperature, pressure, start, end, isolation;

    Button btnUpdateStationParams, btnDeleteStationParams, btnCopyKp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etStationNumberUpdate = findViewById(R.id.etStationNumberUpdate);
        etStationFrequencyUpdate = findViewById(R.id.etStationFrequencyUpdate);
        etStationCurrentUpdate = findViewById(R.id.etStationCurrentUpdate);
        etStationLoadingUpdate = findViewById(R.id.etStationLoadingUpdate);
        etStationZspUpdate = findViewById(R.id.etStationZspUpdate);
        etStationTemperatureUpdate = findViewById(R.id.etStationTemperatureUpdate);
        etStationPressureUpdate = findViewById(R.id.etStationPressureUpdate);
        etStationProgramStartUpdate = findViewById(R.id.etStationProgramStartUpdate);
        etStationProgramEndUpdate = findViewById(R.id.etStationProgramEndUpdate);
        etStationIsolationUpdate = findViewById(R.id.etStationIsolationUpdate);
        etKP = findViewById(R.id.etKP);
        btnUpdateStationParams = findViewById(R.id.btnUpdateStationParams);
        btnDeleteStationParams = findViewById(R.id.btnDeleteStationParams);
        btnCopyKp = findViewById(R.id.btnCopyKP);

        //first call this
        getAndSetIntentData();

        btnUpdateStationParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);

                number = etStationNumberUpdate.getText().toString().trim();
                frequency = etStationFrequencyUpdate.getText().toString().trim();
                current = etStationCurrentUpdate.getText().toString().trim();
                loading = etStationLoadingUpdate.getText().toString().trim();
                zsp = etStationZspUpdate.getText().toString().trim();
                temperature = etStationTemperatureUpdate.getText().toString().trim();
                pressure = etStationPressureUpdate.getText().toString().trim();
                start = etStationProgramStartUpdate.getText().toString().trim();
                end = etStationProgramEndUpdate.getText().toString().trim();
                isolation = etStationIsolationUpdate.getText().toString().trim();

                myDB.updateDataStationHelper(id, number, frequency, current, loading, zsp, temperature, pressure, start, end, isolation);

                //Refresh Activity
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCopyKp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", etKP.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(UpdateActivity.this, "Данные скопированы!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteStationParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id")
                && getIntent().hasExtra("number")
                && getIntent().hasExtra("frequency")
                && getIntent().hasExtra("current")
                && getIntent().hasExtra("loading")
                && getIntent().hasExtra("zsp")
                && getIntent().hasExtra("temperature")
                && getIntent().hasExtra("pressure")
                && getIntent().hasExtra("start")
                && getIntent().hasExtra("end")) {

            //Getting data from Intent
            id = getIntent().getStringExtra("id");
            number = getIntent().getStringExtra("number");
            frequency = getIntent().getStringExtra("frequency");
            current = getIntent().getStringExtra("current");
            loading = getIntent().getStringExtra("loading");
            zsp = getIntent().getStringExtra("zsp");
            temperature = getIntent().getStringExtra("temperature");
            pressure = getIntent().getStringExtra("pressure");
            start = getIntent().getStringExtra("start");
            end = getIntent().getStringExtra("end");
            isolation = getIntent().getStringExtra("isolation");

            String kp = number + " " + frequency + "/" + current + "/" + loading +
                    "/" + zsp + "/" + temperature + "/" + pressure + "/" +
                    start + "-" + end + "/" + isolation;

            //Setting Intent data
            etStationNumberUpdate.setText(number);
            etStationFrequencyUpdate.setText(frequency);
            etStationCurrentUpdate.setText(current);
            etStationLoadingUpdate.setText(loading);
            etStationZspUpdate.setText(zsp);
            etStationTemperatureUpdate.setText(temperature);
            etStationPressureUpdate.setText(pressure);
            etStationProgramStartUpdate.setText(start);
            etStationProgramEndUpdate.setText(end);
            etStationIsolationUpdate.setText(isolation);
            etKP.setText(kp);
        } else {
            Toast.makeText(this, "Данных нет", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить " + number);
        builder.setMessage("Вы уверены, что хотите удалить " + number + "?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteDataStationHelper(id);
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}