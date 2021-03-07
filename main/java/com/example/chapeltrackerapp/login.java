
package com.example.chapeltrackerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapeltrackerapp.DetailedCalendar;
import com.example.chapeltrackerapp.MainActivity;
import com.example.chapeltrackerapp.R;

import java.util.ArrayList;

import static com.example.chapeltrackerapp.MainActivity.PASSWORD;
import static com.example.chapeltrackerapp.MainActivity.PREFS_NAME;
import static com.example.chapeltrackerapp.MainActivity.USER_NAME;


public class login extends AppCompatActivity {

    ChapelsDatabaseHelper chapelsDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void sendHome(View view) {
        EditText userID = (EditText) findViewById(R.id.userID);
        EditText password = (EditText) findViewById(R.id.userPassword);

        // TODO check credentials


        // Edits preferences file with login credentials
        SharedPreferences credentials = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = credentials.edit();
        editor.putString(USER_NAME, String.valueOf(userID));
        editor.putString(PASSWORD, String.valueOf(password));
        editor.apply();

        // After first log in, update database with all chapels
        ArrayList<String> titleList = new ArrayList();
        ArrayList<String> dateList = new ArrayList();
        ArrayList<String> timeList = new ArrayList();
        ArrayList<String> locationList = new ArrayList();
        ArrayList<String> presenterList = new ArrayList();

        WebScrape webScraper = new WebScrape();
        webScraper.updateAllChapels(titleList, dateList, timeList, locationList, presenterList);

        chapelsDatabaseHelper = new ChapelsDatabaseHelper(this);

        for (int i = 0; i < titleList.size(); i++){
            chapelsDatabaseHelper.insertData(titleList.get(i), dateList.get(i), timeList.get(i), locationList.get(i), " temp pres ", 1, 0);
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void sendDetailedCalender(View view) {
        Intent intent = new Intent(this, DetailedCalendar.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
