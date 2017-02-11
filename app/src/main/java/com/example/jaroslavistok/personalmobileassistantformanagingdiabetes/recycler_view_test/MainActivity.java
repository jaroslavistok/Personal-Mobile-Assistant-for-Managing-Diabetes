package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public final int offset = 30;
    private int page = 0;

    private RecyclerView mRecyclerView;
    private boolean loadingMore = false;
    private Toast shortToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        CustomCursorRecyclerViewAdapter mAdapter = new CustomCursorRecyclerViewAdapter(this, null);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        int itemsCountLocal = getItemsCountLocal();
        if (itemsCountLocal == 0) {
            fillTestElements();
        }

        shortToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int maxPositions = layoutManager.getItemCount();

                if (lastVisibleItemPosition == maxPositions - 1) {
                    if (loadingMore)
                        return;

                    loadingMore = true;
                    page++;
                    getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                }
            }
        });

        getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void fillTestElements() {
        int size = 1;
        ContentValues[] listOfContentValues = new ContentValues[size];
        for (int i = 0; i < listOfContentValues.length; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(EntriesTableContract.DATE, "10.10.2017");
            contentValues.put(EntriesTableContract.GLUCOSE_VALUE, "10");
            contentValues.put(EntriesTableContract.CATEGORY, "rano");
            contentValues.put(EntriesTableContract.FAST_INSULIN, "10");
            contentValues.put(EntriesTableContract.SLOW_INSULIN, "10");
            contentValues.put(EntriesTableContract.NOTE, "note");
            contentValues.put(EntriesTableContract.TIME, "time");
            contentValues.put(EntriesTableContract.SYNCHORNIZED, 0);
            listOfContentValues[i] = contentValues;
        }
        getContentResolver().bulkInsert(EntriesProvider.urlForItems(0), listOfContentValues);
    }

    private int getItemsCountLocal() {
        int itemsCount = 0;

        Cursor query = getContentResolver().query(EntriesProvider.urlForItems(0), null, null, null, null);
        if (query != null) {
            itemsCount = query.getCount();
            query.close();
        }
        return itemsCount;
    }

    /*loader*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 0:
                return new CursorLoader(this, EntriesProvider.urlForItems(offset * page), null, null, null, null);
            default:
                throw new IllegalArgumentException("no id handled!");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 0:
                Log.d(TAG, "onLoadFinished: loading MORE");
                shortToast.setText("loading MORE " + page);
                shortToast.show();

                Cursor cursor = ((CustomCursorRecyclerViewAdapter) mRecyclerView.getAdapter()).getCursor();

                //fill all exisitng in adapter
                MatrixCursor mx = new MatrixCursor(EntriesTableContract.Columns);
                fillMatrixCursor(cursor, mx);

                //fill with additional result
                fillMatrixCursor(data, mx);

                ((CustomCursorRecyclerViewAdapter) mRecyclerView.getAdapter()).swapCursor(mx);


                handlerToWait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingMore = false;
                    }
                }, 2000);

                break;
            default:
                throw new IllegalArgumentException("No loader id handled!");
        }
    }

    private Handler handlerToWait = new Handler();

    private void fillMatrixCursor(Cursor data, MatrixCursor matrixCursor) {
        if (data == null)
            return;

        data.moveToPosition(-1);
        while (data.moveToNext()) {
            matrixCursor.addRow(new Object[]{
                    data.getString(data.getColumnIndex(EntriesTableContract._ID)),
                    data.getString(data.getColumnIndex(EntriesTableContract.GLUCOSE_VALUE)),
                    data.getString(data.getColumnIndex(EntriesTableContract.CATEGORY)),
                    data.getString(data.getColumnIndex(EntriesTableContract.DATE)),
                    data.getString(data.getColumnIndex(EntriesTableContract.TIME)),
                    data.getString(data.getColumnIndex(EntriesTableContract.SLOW_INSULIN)),
                    data.getString(data.getColumnIndex(EntriesTableContract.FAST_INSULIN)),
                    data.getString(data.getColumnIndex(EntriesTableContract.NOTE)),
                    data.getString(data.getColumnIndex(EntriesTableContract.SYNCHORNIZED)),
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO: 2016-10-13
    }

    //

    private static final String TAG = "MainActivity";

}