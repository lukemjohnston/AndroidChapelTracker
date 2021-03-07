package com.example.chapeltrackerapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// All the elements in chapelTracker.db are stored in ChapelsDatabaseContract.java
import com.example.chapeltrackerapp.ChapelsDatabaseContract.*;

import java.sql.SQLInput;

public class ChapelsDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "chapelTracker.db";
    public static final int DATABASE_VERSION = 2; // Note: every time you drop the database, you have to increment the version

    // Create Tables
    final String SQL_CREATE_CHAPELINFO_TABLE = "CREATE TABLE " + ChapelEntry.TABLE_NAME + " (" +
            ChapelEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChapelEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            ChapelEntry.COLUMN_PRESENTER + " TEXT, " +
            ChapelEntry.COLUMN_DATE + " TEXT NOT NULL, " +
            ChapelEntry.COLUMN_TIME + " TEXT NOT NULL, " +
            ChapelEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +
            ChapelEntry.COLUMN_CREDIT + " INTEGER NOT NULL, " +
            ChapelEntry.COLUMN_COMPLETED + " INTEGER NOT NULL );";

    public ChapelsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CHAPELINFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChapelEntry.TABLE_NAME);
        onCreate(db);
    }

    public Boolean insertData (String title, String date, String time, String location, String presenter,
        int credit, int completed) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ChapelEntry.COLUMN_TITLE, title);
        values.put(ChapelEntry.COLUMN_DATE, date);
        values.put(ChapelEntry.COLUMN_TIME, time);
        values.put(ChapelEntry.COLUMN_LOCATION, location);
        values.put(ChapelEntry.COLUMN_PRESENTER, presenter);
        values.put(ChapelEntry.COLUMN_CREDIT, credit);
        values.put(ChapelEntry.COLUMN_COMPLETED, completed);

        long result = db.insert(ChapelEntry.TABLE_NAME, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Update Chapel data based on ID of the chapel in the database
    public Boolean updateChapelData (int id, String title, String date, String time, String location, String presenter,
                               int credit, int completed) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ChapelEntry.COLUMN_TITLE, title);
        values.put(ChapelEntry.COLUMN_DATE, date);
        values.put(ChapelEntry.COLUMN_TIME, time);
        values.put(ChapelEntry.COLUMN_LOCATION, location);
        values.put(ChapelEntry.COLUMN_PRESENTER, presenter);
        values.put(ChapelEntry.COLUMN_CREDIT, credit);
        values.put(ChapelEntry.COLUMN_COMPLETED, completed);
        Cursor cursor = db.rawQuery("SELECT * FROM " + ChapelEntry.TABLE_NAME + " WHERE " + ChapelEntry.COLUMN_ID + " = ? ", new String[] {String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.update(ChapelEntry.TABLE_NAME, values, ChapelEntry.COLUMN_ID + " = ? ", new String[] {String.valueOf(id)});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else
            return false;
    }

    public Boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ChapelEntry.TABLE_NAME + " WHERE " + ChapelEntry.COLUMN_ID + " = ? ", new String[] {String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete(ChapelEntry.TABLE_NAME, ChapelEntry.COLUMN_ID + " = ? ", new String[] {String.valueOf(id)});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else
            return false;
    }

    public void clearData (String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getId() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_ID + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getTitle() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_TITLE + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getDate() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_DATE + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getTime() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_TIME + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getLocation() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_LOCATION + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getPresenter() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_PRESENTER + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getCredit() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_CREDIT + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getCompleted() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_COMPLETED + " FROM " + ChapelEntry.TABLE_NAME, null);
        return cursor;
    }

    // Methods for Completed
    public Cursor getTitleCom() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_TITLE + " FROM " + ChapelEntry.TABLE_NAME + " WHERE " +
                ChapelEntry.COLUMN_COMPLETED + " = 1 ", null);
        return cursor;
    }

    public Cursor getDateCom() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_DATE + " FROM " + ChapelEntry.TABLE_NAME + " WHERE " +
                ChapelEntry.COLUMN_COMPLETED + " = 1 ", null);
        return cursor;
    }

    public Cursor getCreditCom() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChapelEntry.COLUMN_CREDIT + " FROM " + ChapelEntry.TABLE_NAME + " WHERE " +
                ChapelEntry.COLUMN_COMPLETED + " = 1 ", null);
        return cursor;
    }

}
