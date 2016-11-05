package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit;


import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RestClient
{
    private static final String BASE_URL = "http://davinci.fmph.uniba.sk/~istok7/";
    private ApiService apiService;

    public RestClient()
    {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        apiService = restAdapter.create(ApiService.class);

        Retrofit restAdapter2 = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = restAdapter2.create(ApiService.class);


        UploadObject uploadObject = new UploadObject("Jaro", "Istok", 100);
        Call<String> exampleCall2 = service.postWithJSON(uploadObject);

        exampleCall2.enqueue(new Callback<String>() {
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

    public ApiService getApiService()
    {
        return apiService;
    }
}