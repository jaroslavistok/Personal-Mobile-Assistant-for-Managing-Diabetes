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

        Call<Integer> exampleCall = apiService.getDummieContent(6);

        UploadObject uploadObject = new UploadObject();
        uploadObject.age = 2;
        uploadObject.name = "Jaro";
        uploadObject.label = "label";
        Call<Integer> exampleCall2 = service.postWithJSON(uploadObject);

        exampleCall2.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.w("upload", "upload response");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.w("upload", "upload failure");
                Log.w("smth", t.getMessage());
                Log.w(call.request().url().toString(), "ee");

            }
        });

        exampleCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.w("on", "response");
                Integer pojoExample = response.body();
                Log.w("on", pojoExample.toString());

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.w("on", "failure");
            }
        });
    }

    public ApiService getApiService()
    {
        return apiService;
    }
}