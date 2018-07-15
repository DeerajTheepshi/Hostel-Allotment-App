package com.example.android.hostellallotment;

import com.example.android.hostellallotment.ModelClasses.Main;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {
    public final String BASE_URL = "http://192.168.1.5:9000/";

    @POST("welcome")
    Call<Main> getLoginInfo(@Body String login_body);

    @POST("register")
    Call<Main> registerANewUser(@Body String user_info);

    @POST("request")
    Call<Main> makeARequest(@Body String user_request);

    @POST("requestMade")
    Call<Main> getARequest(@Body String user_request);

    @GET
    Call<Main> findMatch(@Url String url);
}
