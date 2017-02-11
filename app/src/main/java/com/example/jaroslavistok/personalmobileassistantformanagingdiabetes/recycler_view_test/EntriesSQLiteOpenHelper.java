package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntriesSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "EntriesSQLiteOpenHelper";
    private static final String DATABASE_NAME = "diabetes_manager_db";


    public EntriesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EntriesTableContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EntriesTableContract.DROP_TABLE);
        onCreate(db);
    }

}