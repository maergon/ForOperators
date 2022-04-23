package com.malkollm.foroperators;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "OperatorHelper.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "station_helper";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER_STATION = "station_number";
    public static final String COLUMN_FREQUENCY = "station_frequency";
    public static final String COLUMN_CURRENT = "station_current";
    public static final String COLUMN_LOADING = "station_loading";
    public static final String COLUMN_ZSP = "station_zsp";
    public static final String COLUMN_TEMPERATURE = "station_temperature";
    public static final String COLUMN_PRESSURE = "station_pressure";
    public static final String COLUMN_PROGRAM_START = "station_program_start";
    public static final String COLUMN_PROGRAM_END = "station_program_end";
    public static final String COLUMN_ISOLATION = "station_isolation";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NUMBER_STATION + " INTEGER, " +
                COLUMN_FREQUENCY + " REAL, " +
                COLUMN_CURRENT + " REAL, " +
                COLUMN_LOADING + " REAL, " +
                COLUMN_ZSP + " REAL, " +
                COLUMN_TEMPERATURE + " REAL, " +
                COLUMN_PRESSURE + " REAL, " +
                COLUMN_PROGRAM_START + " REAL, " +
                COLUMN_PROGRAM_END + " REAL, " +
                COLUMN_ISOLATION + " REAL);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addStationParams(int number, float frequency, float current,
                          float loading, float zsp, float temperature,
                          float pressure, float start, float end, float isolation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NUMBER_STATION, number);
        cv.put(COLUMN_FREQUENCY, frequency);
        cv.put(COLUMN_CURRENT, current);
        cv.put(COLUMN_LOADING, loading);
        cv.put(COLUMN_ZSP, zsp);
        cv.put(COLUMN_TEMPERATURE, temperature);
        cv.put(COLUMN_PRESSURE, pressure);
        cv.put(COLUMN_PROGRAM_START, start);
        cv.put(COLUMN_PROGRAM_END, end);
        cv.put(COLUMN_ISOLATION, isolation);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Ошибка сохранения данных в Базу", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void updateData(String row_id, String number, String frequency, String current,
                    String loading, String zsp, String temperature,
                    String pressure, String start, String end, String isolation) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NUMBER_STATION, number);
        cv.put(COLUMN_FREQUENCY, frequency);
        cv.put(COLUMN_CURRENT, current);
        cv.put(COLUMN_LOADING, loading);
        cv.put(COLUMN_ZSP, zsp);
        cv.put(COLUMN_TEMPERATURE, temperature);
        cv.put(COLUMN_PRESSURE, pressure);
        cv.put(COLUMN_PROGRAM_START, start);
        cv.put(COLUMN_PROGRAM_END, end);
        cv.put(COLUMN_ISOLATION, isolation);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка обновления данных", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно обновлены!", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO сделать проверку и вывод тостов в классе Utils через switch case
    void deleteData(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка удаления данных", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные успешно удалены!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
