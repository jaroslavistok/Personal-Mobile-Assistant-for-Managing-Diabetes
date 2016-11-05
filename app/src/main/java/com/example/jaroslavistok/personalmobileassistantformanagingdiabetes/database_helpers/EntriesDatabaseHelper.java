package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts.DatabaseContracts.Entry;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues;


public class EntriesDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "diabetes_db";
    public static final int DATABASE_VERSION = 1;

    public EntriesDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, DefaultsConstantsValues.DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    public EntriesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, DefaultsConstantsValues.DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableSql());

        // inserting some sample data to database

        for (int i = 0; i < 3; i++)
            insertSampleEntry(sqLiteDatabase);


    }

    private String createTableSql() {
        String sqlPrepareTemplate = "CREATE TABLE %s ("
                            + "%s INTEGER PRIMARY KEY AUTOINCREMENT"
                            + "%s VARCHAR(5)"
                            + "%s DATETIME"
                            + ")";

        return String.format(sqlPrepareTemplate, Entry.TABLE_NAME, Entry._ID, Entry.GLUCOSE, Entry.TIMESTAMP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private void insertSampleEntry(SQLiteDatabase db){
        ContentValues sampleContentValues = new ContentValues();
        sampleContentValues.put(Entry.TIMESTAMP, System.currentTimeMillis() / 1000);
        sampleContentValues.put(Entry.GLUCOSE, 12);
        db.insert(Entry.TABLE_NAME, DefaultsConstantsValues.NO_NULL_COLUMN_HACK, sampleContentValues);



    }
}
