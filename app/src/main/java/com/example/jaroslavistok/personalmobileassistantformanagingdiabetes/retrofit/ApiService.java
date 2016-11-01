package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @GET("webservice/")
    Call<Integer> getDummieContent(@Query("dump_param") Integer number);

    @POST("webservice")
    Call<Integer> postWithJSON(@Body UploadObject toUpload);

}