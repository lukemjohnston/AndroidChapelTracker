package com.example.chapeltrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Completed extends AppCompatActivity {

    RecyclerView completedRecView;
    TextView total;
    int chapelsCompletedNum;

    ArrayList<String> dates;
    ArrayList<String> names;
    ArrayList<Integer> credits;

    ChapelsDatabaseHelper chapelsDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapels_completed);

        dates = new ArrayList<>();
        names = new ArrayList<>();
        credits = new ArrayList<>();

        listCompleted();

        completedRecView = findViewById(R.id.completedRecView);

        Completed_RecViewAdpt com_Adaptor = new Completed_RecViewAdpt(this, dates, names, credits);
        completedRecView.setAdapter(com_Adaptor);
        completedRecView.setLayoutManager(new LinearLayoutManager(this));

        total = findViewById(R.id.num_remaining);
        chapelsCompletedNum = com_Adaptor.getItemCount();
        total.setText(String.valueOf(chapelsCompletedNum));

    }

    public void listCompleted() {
        chapelsDatabaseHelper = new ChapelsDatabaseHelper(this);
        SQLiteDatabase db = chapelsDatabaseHelper.getReadableDatabase();

        ArrayList<String> qDates = new ArrayList();
        ArrayList<String> qNames = new ArrayList();
        ArrayList<Integer> qCredits = new ArrayList();

        Cursor res = chapelsDatabaseHelper.getDateCom();
        if (res.getCount() == 0) {
            Toast.makeText(Completed.this, "No Chapels Completed", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qDates.add(res.getString(0));
        }

        res = chapelsDatabaseHelper.getTitleCom();
        if (res.getCount() == 0) {
            Toast.makeText(Completed.this, "No Chapels Completed", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qNames.add(res.getString(0));
        }

        res = chapelsDatabaseHelper.getCreditCom();
        if (res.getCount() == 0) {
            Toast.makeText(Completed.this, "No Chapels Completed", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()) {
            qCredits.add(res.getInt(0));
        }

        res.close();

        dates = qDates;
        names = qNames;
        credits = qCredits;
    }

//    public void listCompleted() {
//        ChapelsDatabaseHelper chapelsDatabaseHelper = new ChapelsDatabaseHelper(this);
//        SQLiteDatabase db = chapelsDatabaseHelper.getReadableDatabase();
//
//        String[] projection = {
//            BaseColumns._ID,
//            ChapelEntry.COLUMN_DATE,
//            ChapelEntry.COLUMN_TITLE,
//            ChapelEntry.COLUMN_CREDIT
//        };
//        String selection = ChapelEntry.COLUMN_CREDIT + " = ?"; // Select where COLUMN_CREDIT = 1
//        String[] selectionArgs = { "1" };
//
//        String sortOrder = ChapelEntry.COLUMN_DATE + " DESC";
//
//        Cursor cursor = db.query(
//                ChapelEntry.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                sortOrder
//        );
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "No completed chapels", Toast.LENGTH_SHORT).show();
//        } else {
//            ArrayList<String> qDates = new ArrayList();
//            ArrayList<String> qNames = new ArrayList();
//            ArrayList<Integer> qCredits = new ArrayList();
//
//            while (cursor.moveToNext()) {
//                String qDate = cursor.getString(
//                        cursor.getColumnIndexOrThrow(ChapelEntry.COLUMN_DATE));
//                qDates.add(qDate);
//                String qName = cursor.getString(
//                        cursor.getColumnIndexOrThrow(ChapelEntry.COLUMN_TITLE));
//                qNames.add(qName);
//                Integer qCredit = cursor.getInt(
//                        cursor.getColumnIndexOrThrow(ChapelEntry.COLUMN_CREDIT));
//                qCredits.add(qCredit);
//            }
//            cursor.close();
//
//            dates = qDates;
//            names = qNames;
//            credits = qCredits;
//        }
//    }

    public int getChapelsCompletedNum () {
        return chapelsCompletedNum;
    }

    public void sendHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void sendDescriptionB(View view) {
        Intent intent = new Intent(this, activity_description.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
