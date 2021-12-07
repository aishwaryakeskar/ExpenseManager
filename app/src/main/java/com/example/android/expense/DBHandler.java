package com.example.android.expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 19-10-18.
 */

public class DBHandler extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "Record";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_MODE = "mode";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_AMOUNT = "amount";

    public static final String TABLE_NAME_SETTINGS = "Settings";
    public static final String COLUMN_ID_SETTINGS = "settingsId";
    public static final String COLUMN_NAME_SETTINGS = "settingsName";
    public static final String COLUMN_VALUE_SETTINGS = "settingsValue";
    //information of database
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "recordDB.db";
    private static DBHandler DB_HANDLER = null;

    //initialize the database
    private DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public static DBHandler getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        if (DB_HANDLER == null) {
            DB_HANDLER = new DBHandler(context, name, factory, version);
        }
        return DB_HANDLER;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_NAME + " TEXT, " + COLUMN_CATEGORY + " TEXT, " + COLUMN_MODE + " TEXT, " + COLUMN_AMOUNT + " INTEGER )";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE_SETTINGS = "CREATE TABLE " + TABLE_NAME_SETTINGS + " ( " + COLUMN_ID_SETTINGS + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_NAME_SETTINGS + " TEXT, " + COLUMN_VALUE_SETTINGS + " INTEGER )";
        db.execSQL(CREATE_TABLE_SETTINGS);

        String INSERT_SETTINGS = "INSERT INTO " + TABLE_NAME_SETTINGS + "(" + COLUMN_NAME_SETTINGS + "," + COLUMN_VALUE_SETTINGS + ") VALUES('limit', 3000)";
        db.execSQL(INSERT_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Record> loadHandler() {
        List<Record> records = new ArrayList<Record>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(0);
            String name = cursor.getString(1);
            String mode = cursor.getString(2);
            String category = cursor.getString(3);
            int amount = cursor.getInt(4);
            Record record = new Record(itemId, name, mode, category, amount);
            records.add(record);
        }
        cursor.close();
        db.close();
        return records;
    }

    public List<Record> groupByHandler() {
        List<Record> chartRecords = new ArrayList<Record>();
        String result = "";
        String query = "SELECT " + COLUMN_CATEGORY + ", SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " GROUP BY  "
                + COLUMN_CATEGORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            String category = cursor.getString(0);
            int amount = cursor.getInt(1);
            Record record = new Record(category, amount);
            chartRecords.add(record);
        }
        cursor.close();
        db.close();
        return chartRecords;
    }

    public void addHandler(Record record) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MODE, record.getMode());
        values.put(COLUMN_CATEGORY, record.getCategory());
        values.put(COLUMN_NAME, record.getItemName());
        values.put(COLUMN_AMOUNT, record.getAmount());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean deleteHandler(int ID) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{
                String.valueOf(ID)
        });
        result = true;
        db.close();
        return result;
    }

    public void setLimit(int limit) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUE_SETTINGS, limit);
        SQLiteDatabase db = this.getWritableDatabase();
        int rowCount = db.update(TABLE_NAME_SETTINGS, values, COLUMN_NAME_SETTINGS + "= 'limit'", null);
        db.close();
    }

    public int getLimit() {
        String query = "SELECT " + COLUMN_VALUE_SETTINGS + " FROM " + TABLE_NAME_SETTINGS + " WHERE " + COLUMN_NAME_SETTINGS + " = 'limit'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int amount = 0;
        while (cursor.moveToNext()) {

            amount = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return amount;
    }

    public int getTotalExpenses() {
        String query = "SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int amount = 0;
        while (cursor.moveToNext()) {

            amount = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return amount;
    }
}