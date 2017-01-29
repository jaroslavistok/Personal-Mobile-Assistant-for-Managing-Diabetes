package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableSql());

        for (int i = 0; i < 2; i++)
            insertSampleEntry(sqLiteDatabase);
    }

    private String createTableSql() {
        String sqlPrepareTemplate = "CREATE TABLE %s ("
                            + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "%s TEXT,"
                            + "%s TEXT"
                            + "%s REAL"
                            + "%s REAL"
                            + "%s REAL"
                            + "%s TEXT"
                            + "%s INT DEFAULT 0"
                            + ")";

        Log.w("sql", sqlPrepareTemplate);

        return String.format(sqlPrepareTemplate,
                Entry.TABLE_NAME,
                Entry._ID,
                Entry.DATE,
                Entry.TIME,
                Entry.GLUCOSE_VALUE,
                Entry.FAST_INSULIN,
                Entry.SLOW_INSULIN,
                Entry.NOTE,
                Entry.SYNCHORNIZED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void insertSampleEntry(SQLiteDatabase db){
        ContentValues sampleContentValues = new ContentValues();
        sampleContentValues.put(Entry.DATE, "YYYY-MM-DD");
        sampleContentValues.put(Entry.TIME, "12:10");
        sampleContentValues.put(Entry.GLUCOSE_VALUE, "10.0");
        sampleContentValues.put(Entry.CATEGORY, "sample");
        sampleContentValues.put(Entry.FAST_INSULIN, "4.5");
        sampleContentValues.put(Entry.SLOW_INSULIN, "6.5");
        sampleContentValues.put(Entry.NOTE, "test note");
        db.insert(Entry.TABLE_NAME, DefaultsConstantsValues.NO_NULL_COLUMN_HACK, sampleContentValues);
    }
}
