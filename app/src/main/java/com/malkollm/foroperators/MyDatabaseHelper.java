package com.malkollm.foroperators;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "OperatorHelper.db";
    private static final int DATABASE_VERSION = 2;

    //Таблица station_helper
    private static final String SH_TABLE_NAME = "station_helper";
    private static final String SH_COLUMN_ID = "_id";
    private static final String SH_COLUMN_NUMBER_STATION = "station_number";
    private static final String SH_COLUMN_FREQUENCY = "station_frequency";
    private static final String SH_COLUMN_CURRENT = "station_current";
    private static final String SH_COLUMN_LOADING = "station_loading";
    private static final String SH_COLUMN_ZSP = "station_zsp";
    private static final String SH_COLUMN_TEMPERATURE = "station_temperature";
    private static final String SH_COLUMN_PRESSURE = "station_pressure";
    private static final String SH_COLUMN_PROGRAM_START = "station_program_start";
    private static final String SH_COLUMN_PROGRAM_END = "station_program_end";
    private static final String SH_COLUMN_ISOLATION = "station_isolation";

    private static final String CREATE_TABLE_STATION_HELPER = "CREATE TABLE " + SH_TABLE_NAME +
            " (" + SH_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SH_COLUMN_NUMBER_STATION + " INTEGER, " +
            SH_COLUMN_FREQUENCY + " REAL, " +
            SH_COLUMN_CURRENT + " REAL, " +
            SH_COLUMN_LOADING + " REAL, " +
            SH_COLUMN_ZSP + " REAL, " +
            SH_COLUMN_TEMPERATURE + " REAL, " +
            SH_COLUMN_PRESSURE + " REAL, " +
            SH_COLUMN_PROGRAM_START + " REAL, " +
            SH_COLUMN_PROGRAM_END + " REAL, " +
            SH_COLUMN_ISOLATION + " REAL);";

    //Таблица station_tasks
    private static final String ST_TABLE_NAME = "station_tasks";
    private static final String ST_COLUMN_ID = "_id";
    private static final String ST_COLUMN_NUMBER_STATION = "station_number";
    private static final String ST_COLUMN_TASK = "station_task";

    private static final String CREATE_TABLE_STATION_TASKS = "CREATE TABLE " + ST_TABLE_NAME +
            " (" + ST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ST_COLUMN_NUMBER_STATION + " INTEGER, " +
            ST_COLUMN_TASK + " TEXT);";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STATION_HELPER);
        db.execSQL(CREATE_TABLE_STATION_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + SH_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ST_TABLE_NAME);
        onCreate(db);
    }

    void addStationParams(int number, float frequency, float current,
                          float loading, float zsp, float temperature,
                          float pressure, float start, float end, float isolation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SH_COLUMN_NUMBER_STATION, number);
        cv.put(SH_COLUMN_FREQUENCY, frequency);
        cv.put(SH_COLUMN_CURRENT, current);
        cv.put(SH_COLUMN_LOADING, loading);
        cv.put(SH_COLUMN_ZSP, zsp);
        cv.put(SH_COLUMN_TEMPERATURE, temperature);
        cv.put(SH_COLUMN_PRESSURE, pressure);
        cv.put(SH_COLUMN_PROGRAM_START, start);
        cv.put(SH_COLUMN_PROGRAM_END, end);
        cv.put(SH_COLUMN_ISOLATION, isolation);

        long result = db.insert(SH_TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Ошибка сохранения данных в Базу", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();
        }
    }

    void addStationTask(int number, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ST_COLUMN_NUMBER_STATION, number);
        cv.put(ST_COLUMN_TASK, task);

        long result = db.insert(ST_TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Ошибка сохранения данных в Базу", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllDataStationHelper() {
        String query = "SELECT * FROM " + SH_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readAllDataStationTask() {
        String query = "SELECT * FROM " + ST_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void updateDataStationHelper(String row_id, String number, String frequency, String current,
                                 String loading, String zsp, String temperature,
                                 String pressure, String start, String end, String isolation) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SH_COLUMN_NUMBER_STATION, number);
        cv.put(SH_COLUMN_FREQUENCY, frequency);
        cv.put(SH_COLUMN_CURRENT, current);
        cv.put(SH_COLUMN_LOADING, loading);
        cv.put(SH_COLUMN_ZSP, zsp);
        cv.put(SH_COLUMN_TEMPERATURE, temperature);
        cv.put(SH_COLUMN_PRESSURE, pressure);
        cv.put(SH_COLUMN_PROGRAM_START, start);
        cv.put(SH_COLUMN_PROGRAM_END, end);
        cv.put(SH_COLUMN_ISOLATION, isolation);

        long result = db.update(SH_TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка обновления данных", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно обновлены!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateDataStationTask(String row_id, String number, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ST_COLUMN_NUMBER_STATION, number);
        cv.put(ST_COLUMN_TASK, task);

        long result = db.update(ST_TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка обновления данных", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно обновлены!", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO сделать проверку и вывод тостов в классе Utils через switch case
    void deleteDataStationHelper(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(SH_TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка удаления данных", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно удалены!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteDataStationTask(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(ST_TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка удаления данных", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно удалены!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllDataStationHelper() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + SH_TABLE_NAME);
    }

    void deleteAllDataStationTask() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + ST_TABLE_NAME);
    }
}
