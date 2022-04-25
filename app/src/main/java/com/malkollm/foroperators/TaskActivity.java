package com.malkollm.foroperators;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    CustomAdapterST customAdapterST;

    ImageView ivEmpty;
    TextView tvNoData;

    MyDatabaseHelper myDB;
    ArrayList<String> task_id, station_number, station_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerView.findViewById(R.id.rvTasks);
        addButton.findViewById(R.id.btnAddTask);
        ivEmpty = findViewById(R.id.iv_empty);
        tvNoData = findViewById(R.id.tvNoData);
    }
}