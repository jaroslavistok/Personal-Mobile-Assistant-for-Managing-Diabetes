package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts.DatabaseContracts;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.providers.EntriesContentProvider;

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

    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 1L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;

    private static final int NOTES_LOADER_ID = 0;

    Account mAccount;

    private ListAdapter initializeEntriesAdapter(){

        // Column data from cursor to bind views from
        String[] from = { DatabaseContracts.Entry.GLUCOSE, DatabaseContracts.Entry.TIMESTAMP };
        int[] to = {android.R.id.text1, android.R.id.text2};
        entriesViewAdapter = new SimpleCursorAdapter(this, android.R.layout.list_content, NO_CURSOR, from, to, NO_FLAGS);
        return entriesViewAdapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(NOTES_LOADER_ID, Bundle.EMPTY, this);

        entriesListView = (ListView) findViewById(R.id.entriesListView);
        entriesListView.setAdapter(initializeEntriesAdapter());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAccount = CreateSyncAccount(this);

        Log.w("Warning", "After content resolver");

        ContentResolver.setIsSyncable(mAccount, AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
        ContentResolver.requestSync(mAccount, AUTHORITY, Bundle.EMPTY);
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

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(EntriesContentProvider.CONTENT_URI);
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

}
