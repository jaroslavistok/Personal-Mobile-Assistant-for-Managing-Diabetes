package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts.DatabaseContracts;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_helpers.EntriesDatabaseHelper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.content.ContentResolver.SCHEME_CONTENT;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.ALL_COLUMNS;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_CONTENT_OBSERVER;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_GROUP_BY;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_HAVING;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_NULL_COLUMN_HACK;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_SELECTION;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_SELECTION_ARGS;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_SORT_ORDER;

/* Content provider funguje podobne ako web server, na data sa dopytujeme pomocou URL */
public class EntriesContentProvider extends ContentProvider {

    private EntriesDatabaseHelper databaseHelper;

    /* autorita content providera - zaklad url adresy */
    public static final String AUTHORITY = "com.example.android.datasync.provider";
    public static final Uri CONTENT_URI = new Uri.Builder()
                                                .scheme(SCHEME_CONTENT)
                                                .authority(AUTHORITY)
                                                .appendPath(DatabaseContracts.Entry.TABLE_NAME)
                                                .build();

    public EntriesContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        SQLiteDatabase writeableDatabase = databaseHelper.getWritableDatabase();
        long insertedEntryItem = writeableDatabase.insert(DatabaseContracts.Entry.TABLE_NAME, NO_NULL_COLUMN_HACK, values);
        Log.w("inserted", String.valueOf(insertedEntryItem));
        Uri insertedEntryUri =  ContentUris.withAppendedId(CONTENT_URI, insertedEntryItem);

        getContext().getContentResolver().notifyChange(insertedEntryUri, NO_CONTENT_OBSERVER);

        return ContentUris.withAppendedId(CONTENT_URI, insertedEntryItem);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new EntriesDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DatabaseContracts.Entry.TABLE_NAME, ALL_COLUMNS,
                NO_SELECTION, NO_SELECTION_ARGS, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
