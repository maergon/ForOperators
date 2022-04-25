package com.malkollm.foroperators;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterST extends RecyclerView.Adapter<CustomAdapterST.MyViewHolder> {
    Context context;
    Activity activity;
    Animation translate_anim;

    ArrayList station_id, station_number, station_frequency,
            station_current, station_loading, station_zsp, station_temperature,
            station_pressure, station_start, station_end, station_isolation;

    public CustomAdapterST(Activity activity, Context context, ArrayList station_id, ArrayList station_number,
                           ArrayList station_frequency, ArrayList station_current, ArrayList station_loading,
                           ArrayList station_zsp, ArrayList station_temperature, ArrayList station_pressure,
                           ArrayList station_start, ArrayList station_end, ArrayList station_isolation) {
        this.activity = activity;
        this.context = context;
        this.station_id = station_id;
        this.station_number = station_number;
        this.station_frequency = station_frequency;
        this.station_current = station_current;
        this.station_loading = station_loading;
        this.station_zsp = station_zsp;
        this.station_temperature = station_temperature;
        this.station_pressure = station_pressure;
        this.station_start = station_start;
        this.station_end = station_end;
        this.station_isolation = station_isolation;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.station_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvStationNumber.setText(String.valueOf(station_number.get(position)));
        holder.tvStationFrequency.setText(String.valueOf(station_frequency.get(position)));
        holder.tvStationCurrent.setText(String.valueOf(station_current.get(position)));
        holder.tvStationLoading.setText(String.valueOf(station_loading.get(position)));
        holder.tvStationZsp.setText(String.valueOf(station_zsp.get(position)));
        holder.tvStationTemperature.setText(String.valueOf(station_temperature.get(position)));
        holder.tvStationPressure.setText(String.valueOf(station_pressure.get(position)));
        holder.tvStationStart.setText(String.valueOf(station_start.get(position)));
        holder.tvStationEnd.setText(String.valueOf(station_end.get(position)));
        holder.tvStationIsolation.setText(String.valueOf(station_isolation.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(station_id.get(position)));
                intent.putExtra("number", String.valueOf(station_number.get(position)));
                intent.putExtra("frequency", String.valueOf(station_frequency.get(position)));
                intent.putExtra("current", String.valueOf(station_current.get(position)));
                intent.putExtra("loading", String.valueOf(station_loading.get(position)));
                intent.putExtra("zsp", String.valueOf(station_zsp.get(position)));
                intent.putExtra("temperature", String.valueOf(station_temperature.get(position)));
                intent.putExtra("pressure", String.valueOf(station_pressure.get(position)));
                intent.putExtra("start", String.valueOf(station_start.get(position)));
                intent.putExtra("end", String.valueOf(station_end.get(position)));
                intent.putExtra("isolation", String.valueOf(station_isolation.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return station_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStationNumber, tvStationFrequency, tvStationCurrent,
                tvStationLoading, tvStationZsp, tvStationTemperature,
                tvStationPressure, tvStationStart, tvStationEnd,
                tvStationIsolation;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStationNumber = itemView.findViewById(R.id.tv_station_number);
            tvStationFrequency = itemView.findViewById(R.id.tv_station_frequency);
            tvStationCurrent = itemView.findViewById(R.id.tv_station_current);
            tvStationLoading = itemView.findViewById(R.id.tv_station_loading);
            tvStationZsp = itemView.findViewById(R.id.tv_station_zsp);
            tvStationTemperature = itemView.findViewById(R.id.tv_station_temperature);
            tvStationPressure = itemView.findViewById(R.id.tv_station_pressure);
            tvStationStart = itemView.findViewById(R.id.tv_station_start);
            tvStationEnd = itemView.findViewById(R.id.tv_station_end);
            tvStationIsolation = itemView.findViewById(R.id.tv_station_isolation);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate Recyclerview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
