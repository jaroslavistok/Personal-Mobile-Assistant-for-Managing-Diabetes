package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.synchronization;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit.RestClient;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */

        Log.w("something", "sync adapter construktor");
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.w("kwdkkw", "onPerformsync method");

        RestClient restClient = new RestClient();
        restClient.setContentProviderClient(contentProviderClient);
//        restClient.getDataFromContentProvider();
        restClient.synchronizeData();

        Log.w("dwdww", "Tokenik");
    }
}