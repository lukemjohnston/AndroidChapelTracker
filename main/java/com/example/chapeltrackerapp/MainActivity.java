package com.example.chapeltrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Name of Login Credentials preference file
    public static final String PREFS_NAME = "LoginCredentials";
    public static final String USER_NAME = "BiolaID";
    public static final String PASSWORD = "Password";

    RecyclerView mainRecView;

    ArrayList<String> titles;
    ArrayList<String> times;
    ArrayList<String> locations;
    ArrayList<String> dates;

    public SQLiteDatabase mDatabase;
    ChapelsDatabaseHelper mDatabaseHelper;

    TextView numChapelsCompleted;
    TextView numChapelsRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // On create, update all completed chapel information in database, and then set view
        SharedPreferences credentials = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = credentials.getString(USER_NAME, null);
        String password = credentials.getString(PASSWORD, null);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database Clearing
        mDatabaseHelper = new ChapelsDatabaseHelper(MainActivity.this);

        // Database Intialization
//        mDatabaseHelper.insertData("Morning Ses", "9/21", "10:00 am", "Sutherland", "Dr. Barry Correy", 1, 1);
//        mDatabaseHelper.insertData("Evening Ses", "9/21", "4:30 pm", "Sutherland", "Chris Grace", 1, 0);
//        mDatabaseHelper.insertData("Fives", "9/22", "5:00 pm", "Calvary", "Todd Picket", 1, 0);
//        mDatabaseHelper.insertData("Morning Ses", "9/23", "10:00 am", "Sutherland", "Kanye West", 1, 0);
//        mDatabaseHelper.insertData("Wed Evening", "9/23", "8:45 pm", "Sutherland", "Big Chungus", 1, 1);
//        mDatabaseHelper.insertData("Fives", "9/24", "5:00 pm", "Calvary", "Todd Picket", 1, 1);
//        mDatabaseHelper.insertData("Morning Ses", "9/25", "10:00 am", "Sutherland", "Grace Lew", 1, 1);
//        mDatabaseHelper.insertData("Singspiration", "9/27", "8:00 pm", "Gym", "For All Seasons", 1, 1);
//
//        mDatabaseHelper.insertData("Morning Ses", "9/28", "10:00 am", "Sutherland", "Aaron Georgeson", 1, 0);
//        mDatabaseHelper.insertData("Evening Ses", "9/28", "4:30 pm", "Sutherland", "Chris Grace", 1, 1);
//        mDatabaseHelper.insertData("Fives", "9/29", "5:00 pm", "Calvary", "Todd Picket", 1, 0);
//        mDatabaseHelper.insertData("Morning Ses", "9/30", "10:00 am", "Sutherland", "Master Chief", 1, 1);
//        mDatabaseHelper.insertData("Wed Evening", "9/30", "8:45 pm", "Sutherland", "Luke Johnston", 1, 1);
//        mDatabaseHelper.insertData("Fives", "10/1", "5:00 pm", "Calvary", "Todd Picket", 1, 1);
//        mDatabaseHelper.insertData("Morning Ses", "10/2", "10:00 am", "Sutherland", "Glub the Nub", 1, 0);
//        mDatabaseHelper.insertData("Singspiration", "10/4", "8:00 pm", "Gym", "Sleeping at Last", 1, 1);

//          mDatabaseHelper.insertData("Testing Ses", "10/8", "12:00 pm", "Gym", "Your Boi", 1, 0);





        // Mini Calendar Intialization

        titles = new ArrayList<>(3);
        times = new ArrayList<>(3);
        locations = new ArrayList<>(3);
        dates = new ArrayList<>(3);

        miniCalendar();

        mainRecView = findViewById(R.id.recyclerViewer);

        main_RecViewAdpt det_Adaptor = new main_RecViewAdpt(this, titles, times, locations, dates);
        mainRecView.setAdapter(det_Adaptor);
        mainRecView.setLayoutManager(new LinearLayoutManager(this));

        // Date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateForm = new SimpleDateFormat("EEEE MM/dd");
        String todayDate = dateForm.format(calendar.getTime());
        TextView date = findViewById(R.id.home_date);
        date.setText(todayDate);

        // Handling Number of Chapels Remaining
        numChapelsRemaining = findViewById(R.id.num_remaining);
        int temp1 = det_Adaptor.getItemCount();
        numChapelsRemaining.setText(String.valueOf(temp1));

        // Handling Number of Chapels Completed
        numChapelsCompleted = findViewById(R.id.num_completed);
        Cursor res = mDatabaseHelper.getDateCom();
        int temp2 = res.getCount();
        numChapelsCompleted.setText(String.valueOf(temp2));

//
//        Boolean checkinsertdata = mDatabaseHelper.insertData(mDatabase);
//        if (checkinsertdata == true) {
//            Toast.makeText(MainActivity.this, "Database initialized", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MainActivity.this, "Database NOT initialized", Toast.LENGTH_SHORT).show();
//        }

    }

    public void refreshHome(View view) {
        Intent refresh = getIntent();
        finish();
        startActivity(refresh);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void miniCalendar() {
        mDatabaseHelper = new ChapelsDatabaseHelper(this);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        ArrayList<String> qTitles = new ArrayList(3);
        ArrayList<String> qTimes = new ArrayList(3);
        ArrayList<String> qLocations = new ArrayList(3);
        ArrayList<String> qDates = new ArrayList(3);

        Cursor res = mDatabaseHelper.getTitle();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qTitles.add(res.getString(0));
        }

        res = mDatabaseHelper.getTime();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qTimes.add(res.getString(0));
        }

        res = mDatabaseHelper.getLocation();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qLocations.add(res.getString(0));
        }

        res = mDatabaseHelper.getDate();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Chapels Remaining", Toast.LENGTH_SHORT).show();
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

    public void sendSettings(View view) {
        Intent intent = new Intent(this, activity_setting.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void sendDescription(View view) {
        Intent intent = new Intent(this, activity_description.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void sendDetailedCalender(View view) {
        Intent intent = new Intent(this, DetailedCalendar.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void sendCompleted(View view) {
        Intent intent = new Intent(this, Completed.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}