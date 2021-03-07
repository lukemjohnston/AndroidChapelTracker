package com.example.chapeltrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailedCalendar extends AppCompatActivity {

    RecyclerView detailedCalRecView;
    //String titles[], times[], locations[], dates2[];

    ArrayList<String> titles;
    ArrayList<String> times;
    ArrayList<String> locations;
    ArrayList<String> dates;

    ChapelsDatabaseHelper chapelsDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_calendar);

        titles = new ArrayList<>();
        times = new ArrayList<>();
        locations = new ArrayList<>();
        dates = new ArrayList<>();

        totalChapels();

        detailedCalRecView = findViewById(R.id.recycler_view);

        DetailedCal_RecViewAdpt det_Adaptor = new DetailedCal_RecViewAdpt(this, titles, times, locations, dates);
        detailedCalRecView.setAdapter(det_Adaptor);
        detailedCalRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void totalChapels() {
        chapelsDatabaseHelper = new ChapelsDatabaseHelper(this);
        SQLiteDatabase db = chapelsDatabaseHelper.getReadableDatabase();

        ArrayList<String> qTitles = new ArrayList();
        ArrayList<String> qTimes = new ArrayList();
        ArrayList<String> qLocations = new ArrayList();
        ArrayList<String> qDates = new ArrayList();

        Cursor res = chapelsDatabaseHelper.getTitle();
        if (res.getCount() == 0) {
            Toast.makeText(DetailedCalendar.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qTitles.add(res.getString(0));
        }

        res = chapelsDatabaseHelper.getTime();
        if (res.getCount() == 0) {
            Toast.makeText(DetailedCalendar.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qTimes.add(res.getString(0));
        }

        res = chapelsDatabaseHelper.getLocation();
        if (res.getCount() == 0) {
            Toast.makeText(DetailedCalendar.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qLocations.add(res.getString(0));
        }

        res = chapelsDatabaseHelper.getDate();
        if (res.getCount() == 0) {
            Toast.makeText(DetailedCalendar.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qDates.add(res.getString(0));
        }

        res.close();

        titles = qTitles;
        times = qTimes;
        locations = qLocations;
        dates = qDates;
    }


    public void sendHome(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void sendDescriptionC(View view) {
        Intent intent = new Intent(this, activity_description.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}

