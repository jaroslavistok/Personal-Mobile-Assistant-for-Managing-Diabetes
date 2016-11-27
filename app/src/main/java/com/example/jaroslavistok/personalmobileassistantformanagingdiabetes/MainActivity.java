package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts.DatabaseContracts;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.providers.EntriesContentProvider;

import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.DISMISS_ACTION;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_COOKIE;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_CURSOR;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_FLAGS;

public class MainActivity extends AppCompatActivity implements  android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter entriesViewAdapter;
    private ListView entriesListView;



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
        Log.w("exotic", "adapter initialization");
        String[] from = {DatabaseContracts.Entry.GLUCOSE, DatabaseContracts.Entry.TIMESTAMP};
        int[] to = {R.id.glucose, R.id.time};
        entriesViewAdapter = new SimpleCursorAdapter(this, R.layout.entry_item, NO_CURSOR, from, to, NO_FLAGS);
        return entriesViewAdapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SyncContentObserver observer = new SyncContentObserver();
        getContentResolver().registerContentObserver(EntriesContentProvider.CONTENT_URI, true, observer);

        entriesListView = (ListView) findViewById(R.id.entriesListView);
        entriesListView.setAdapter(initializeEntriesAdapter());

        getLoaderManager().initLoader(NOTES_LOADER_ID, Bundle.EMPTY, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                createNewNote();
            }
        });

        mAccount = CreateSyncAccount(this);

        Log.w("Warning", "After content resolver");

        ContentResolver.setIsSyncable(mAccount, AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                SYNC_INTERVAL);

        Log.w("Requested sync", "Requested sync");
    }

    public static Account CreateSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null))
            return newAccount;

        return newAccount;
    }


    private void createNewNote() {
        final EditText descriptionEditText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add a new note")
                .setView(descriptionEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String description = descriptionEditText.getText().toString();
                        insertIntoContentProvider(description);
                    }
                })
                .setNegativeButton("Cancel", DISMISS_ACTION)
                .show();
    }

    private void insertIntoContentProvider(String glucose) {
        Uri uri = EntriesContentProvider.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(DatabaseContracts.Entry.GLUCOSE, glucose);

        AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(MainActivity.this, "Note was saved", Toast.LENGTH_SHORT)
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
        this.entriesViewAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        this.entriesViewAdapter.swapCursor(NO_CURSOR);
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
