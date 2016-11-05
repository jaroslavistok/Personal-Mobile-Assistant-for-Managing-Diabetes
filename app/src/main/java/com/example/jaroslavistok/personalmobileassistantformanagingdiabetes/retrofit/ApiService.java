package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {
//    @GET("webservice/")
//    Call<Integer> getDummieContent(@Query("dump_param") Integer number);

    @POST("webservice/index.php")
    Call<String> postWithJSON(@Body UploadObject toUpload);
}