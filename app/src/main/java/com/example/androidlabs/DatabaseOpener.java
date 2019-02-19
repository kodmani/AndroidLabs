package com.example.androidlabs;

import android.app.ActionBar;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpener extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "ChatDatabaseFile";
    public final static int VERSION_NUMBER = 1;
    public final static String TABLE_NAME = "ChatTable";
    public final static String COL_ID = "_id";
    public final static String COL_MESSAGE = "MESSAGE";


    public DatabaseOpener(Activity ctx){
        //The factory parameter should be null, unless you know a lot about Database Memory management
        super(ctx, DATABASE_NAME, null, VERSION_NUMBER);
    }

    public void onCreate(SQLiteDatabase db)
    {
        //Create table
        //Make sure you put spaces between SQL statements and Java strings:
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_MESSAGE  + " TEXT) ");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database upgrade", "Old version: " + oldVersion + "New Version: " + newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Downgrade Database", "Old Version: " + oldVersion + "New version: " + newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
}
