package com.malkollm.foroperators;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    CustomAdapterSH customAdapterSH;

    ImageView ivEmpty;
    TextView tvNoData;

    MyDatabaseHelper myDB;
    ArrayList<String> station_id, station_number, station_frequency,
            station_current, station_loading, station_zsp, station_temperature,
            station_pressure, station_start, station_end, station_isolation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.add_button);
        ivEmpty = findViewById(R.id.iv_empty);
        tvNoData = findViewById(R.id.tvNoData);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        station_id = new ArrayList<>();
        station_number = new ArrayList<>();
        station_frequency = new ArrayList<>();
        station_current = new ArrayList<>();
        station_loading = new ArrayList<>();
        station_zsp = new ArrayList<>();
        station_temperature = new ArrayList<>();
        station_pressure = new ArrayList<>();
        station_start = new ArrayList<>();
        station_end = new ArrayList<>();
        station_isolation = new ArrayList<>();

        storeDataInArray();

        customAdapterSH = new CustomAdapterSH(MainActivity.this, this, station_id,
                station_number, station_frequency,
                station_current, station_loading, station_zsp, station_temperature,
                station_pressure, station_start, station_end, station_isolation);
        recyclerView.setAdapter(customAdapterSH);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    //пересоздание активити для обновления отображаемой информации
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArray() {
        Cursor cursor = myDB.readAllDataStationHelper();

        if (cursor.getCount() == 0) {
            ivEmpty.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                station_id.add(cursor.getString(0));
                station_number.add(cursor.getString(1));
                station_frequency.add(cursor.getString(2));
                station_current.add(cursor.getString(3));
                station_loading.add(cursor.getString(4));
                station_zsp.add(cursor.getString(5));
                station_temperature.add(cursor.getString(6));
                station_pressure.add(cursor.getString(7));
                station_start.add(cursor.getString(8));
                station_end.add(cursor.getString(9));
                station_isolation.add(cursor.getString(10));
            }

            ivEmpty.setVisibility(View.GONE);
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.getKP:
                dialogGetKp();
                break;
            case R.id.deleteAll:
                confirmDialogDeleteAll();
                break;
            case R.id.showTasks:
                showTasks();
            default:
                break;
        }

//        if (item.getItemId() == R.id.deleteAll) {
//            confirmDialogDeleteAll();
//        }

        return super.onOptionsItemSelected(item);
    }

    private void showTasks() {
        Intent intent = new Intent(MainActivity.this, TaskActivity.class);
        startActivity(intent);
        finish();
    }

    private void dialogGetKp() {
        //TODO в разработке
        LayoutInflater li = LayoutInflater.from(this);
        View promptView = li.inflate(R.layout.prompt, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(promptView);

        builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    //TODO скрывать иконку если нет данных для удаления
    private void confirmDialogDeleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить все данные");
        builder.setMessage("Вы уверены, что хотите удалить все?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllDataStationHelper();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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