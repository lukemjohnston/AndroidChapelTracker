package com.example.chapeltrackerapp;

import android.provider.BaseColumns;

public class ChapelsDatabaseContract {

    private ChapelsDatabaseContract() {}

    // These define the contents of each table
    public static final class ChapelEntry implements BaseColumns {
        public static final String TABLE_NAME = "chapelInformation";
        public static final String COLUMN_ID = "id"; // Int
        public static final String COLUMN_TITLE = "title"; // Text
        public static final String COLUMN_DATE = "date"; // Text: Form 00/00
        public static final String COLUMN_TIME = "time"; // Text: 00:00 am or pm
        public static final String COLUMN_LOCATION = "location"; // Text
        public static final String COLUMN_PRESENTER = "presenter"; // Text
        public static final String COLUMN_CREDIT = "credit"; // Int
        public static final String COLUMN_COMPLETED = "Completed"; // Int: 1 or 0 since no Bool in SQLite
    }
}
