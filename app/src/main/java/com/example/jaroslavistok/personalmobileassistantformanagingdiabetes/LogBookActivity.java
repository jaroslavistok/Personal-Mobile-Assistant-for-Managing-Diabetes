package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.LogBookAdapter;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts.DatabaseContracts;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.providers.EntriesContentProvider;

import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_COOKIE;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_CURSOR;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_FLAGS;

public class LogBookActivity extends AppCompatActivity implements  android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter entriesViewAdapter;
    private LogBookAdapter logBookAdapter;
//    private ListView entriesListView;

    // Constants
    // The authority for the sync listViewAdapter's content provider
    public static final String AUTHORITY = EntriesContentProvider.AUTHORITY;
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "example.com";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields

    public static final int INSERT_NOTE_TOKEN = 0;

    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 1L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;

    private static final int NOTES_LOADER_ID = 0;

    Account mAccount;

    private ListAdapter initializeEntriesAdapter(){
        String[] from = {DatabaseContracts.Entry.DATE, DatabaseContracts.Entry.CATEGORY, DatabaseContracts.Entry.GLUCOSE_VALUE};
        int[] to = {R.id.date, R.id.category, R.id.date};
        entriesViewAdapter = new SimpleCursorAdapter(this, R.layout.row_layout, NO_CURSOR, from, to, NO_FLAGS);
        return entriesViewAdapter;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);


        SyncContentObserver observer = new SyncContentObserver();
        getContentResolver().registerContentObserver(EntriesContentProvider.CONTENT_URI, true, observer);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LogBookAdapter adapter = new LogBookAdapter(this, null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.w("Recycler view shown", "Recycler view show");

        getLoaderManager().initLoader(NOTES_LOADER_ID, Bundle.EMPTY, this);


        mAccount = CreateSyncAccount(this);

        Log.w("Warning", "After content resolver");

        ContentResolver.setIsSyncable(mAccount, AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
//        ContentResolver.addPeriodicSync(
//                mAccount,
//                AUTHORITY,
//                Bundle.EMPTY,
//                SYNC_INTERVAL);

        Log.w("Requested sync", "Requested sync");

    }

    public static Account CreateSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null))
            return newAccount;

        return newAccount;
    }


    private void insertIntoContentProvider(String category, String glucose) {
        Uri uri = EntriesContentProvider.CONTENT_URI;
        ContentValues values = new ContentValues();
//        values.put(DatabaseContracts.Entry.GLUCOSE, glucose);
        values.put(DatabaseContracts.Entry.CATEGORY, category);

        AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(LogBookActivity.this, "Note was saved", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        insertHandler.startInsert(INSERT_NOTE_TOKEN, NO_COOKIE, uri, values);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(EntriesContentProvider.CONTENT_URI);
        Log.w("content_uri", "Content loader uri: "+ EntriesContentProvider.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
//        this.entriesViewAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
//        this.entriesViewAdapter.swapCursor(NO_CURSOR);
    }


    public class SyncContentObserver extends ContentObserver {
        public SyncContentObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri changeUri) {
            Log.w("onchange", changeUri.toString());
            ContentResolver.requestSync(mAccount, AUTHORITY, Bundle.EMPTY);
        }
    }

}
