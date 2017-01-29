package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts.DatabaseContracts;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.providers.EntriesContentProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_PROJECTION;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_SELECTION;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_SELECTION_ARGS;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_SORT_ORDER;


public class RestClient {
    private ContentProviderClient contentProviderClient;

    private static final String BASE_URL = "http://davinci.fmph.uniba.sk/~istok7/";

    private ApiService apiService;

    private List<String> glucoses;
    private List<String> dateTimes;
    private List<String> categories;

    public void getDataFromContentProvider(){
        Cursor cursor = null;
        try {
            cursor = this.contentProviderClient.query(EntriesContentProvider.CONTENT_URI, NO_PROJECTION,
                    NO_SELECTION, NO_SELECTION_ARGS, NO_SORT_ORDER);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        glucoses = new ArrayList<>();
        dateTimes = new ArrayList<>();
        categories = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            cursor.moveToLast();

            while (!cursor.isAfterLast()) {
//                glucoses.add(cursor.getString(cursor.getColumnIndex(DatabaseContracts.Entry.GLUCOSE)));
//                dateTimes.add(cursor.getString(cursor.getColumnIndex(DatabaseContracts.Entry.TIMESTAMP)));
                categories.add(cursor.getString(cursor.getColumnIndex(DatabaseContracts.Entry.CATEGORY)));
                cursor.moveToNext();
            }

            cursor.close();
        }
    }

    public RestClient()
    {
        Retrofit restApiAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = restApiAdapter.create(ApiService.class);

    }

    public void synchronizeData() {

        for(int i = 0; i < glucoses.size(); i++){
            EntryData entry = new EntryData();
            entry.setGlucose(glucoses.get(i));
            entry.setDateTime(dateTimes.get(i));
            entry.setCategory(categories.get(i));
            Log.w("dd", categories.get(i));

            Call<String> uploadDataCall = apiService.postWithJSON(entry);

            uploadDataCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.w("upload", "upload response");
                    String responsedata = response.body();
                    Log.w("on", responsedata);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.w("upload", "upload failure");
                    Log.w("smth", t.getMessage());
                    Log.w(call.request().url().toString(), "ee");

                }
            });

        }
    }

    public ApiService getApiService()
    {
        return apiService;
    }

    public void setContentProviderClient(ContentProviderClient contentProviderClient){
        this.contentProviderClient = contentProviderClient;
    }

}