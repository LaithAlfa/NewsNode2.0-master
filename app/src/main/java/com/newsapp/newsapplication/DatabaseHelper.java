package com.newsapp.newsapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String FIRSTNAME_TABLE = "demmo.sqlite";
    private static int DB_SCHEME_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, FIRSTNAME_TABLE, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create user table
        db.execSQL(DatabaseManagerUser.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(String.format("DROP TABLE IF EXISTS%s", FIRSTNAME_TABLE));
        onCreate(db);
    }
}

