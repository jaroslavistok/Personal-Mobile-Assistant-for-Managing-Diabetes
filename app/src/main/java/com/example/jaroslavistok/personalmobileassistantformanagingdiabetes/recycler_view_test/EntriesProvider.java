package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test;

import android.app.BuildConfig;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class EntriesProvider extends ContentProvider {
    private static final String TAG = "EntriesProvider";
    private SQLiteOpenHelper sqlLiteHelper;
    private static final UriMatcher uriMatcher;
    public static final String AUTHORITY = "com.example.android.test.provider";
    private static final int TABLE_ITEMS = 0;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, EntriesTableContract.TABLE_NAME + "/offset/" + "#", TABLE_ITEMS);
    }

    public static Uri urlForItems(int limit) {
        return Uri.parse("content://" + AUTHORITY + "/" + EntriesTableContract.TABLE_NAME + "/offset/" + limit);
    }

    @Override
    public boolean onCreate() {
        sqlLiteHelper = new EntriesSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    synchronized public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = sqlLiteHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder;
        queryBuilder = new SQLiteQueryBuilder();
        Cursor cursor = null;
        String offset;

        switch (uriMatcher.match(uri)) {
            case TABLE_ITEMS: {
                queryBuilder.setTables(EntriesTableContract.TABLE_NAME);
                offset = uri.getLastPathSegment();
                break;
            }
            default:
                throw new IllegalArgumentException("Uri not recognized!");
        }

        int intOffset = Integer.parseInt(offset);

        String limitArg = intOffset + ", " + 30;
        Log.d(TAG, "query: " + limitArg);
        cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder, limitArg);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return BuildConfig.APPLICATION_ID + ".item";
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        String table = "";
        switch (uriMatcher.match(uri)) {
            case TABLE_ITEMS: {
                table = EntriesTableContract.TABLE_NAME;
                break;
            }
        }

        long result = sqlLiteHelper.getWritableDatabase().insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        if (result == -1) {
            throw new SQLException("Insert with conflict!");
        }

        return ContentUris.withAppendedId(uri, result);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return -1;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return -1;
    }
}