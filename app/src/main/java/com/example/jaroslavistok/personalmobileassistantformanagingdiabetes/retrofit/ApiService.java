package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("webservice/request_handler.php")
    Call<String> postWithJSON(@Body EntryData toUpload);
}