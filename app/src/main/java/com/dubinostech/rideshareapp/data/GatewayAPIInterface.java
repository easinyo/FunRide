package com.dubinostech.rideshareapp.data;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface GatewayAPIInterface {




    ///<version name>/users/login
    //@Headers({"Content-Type: application/json"})


    @Headers({
            "Content-Type: application/json",
            "application-id: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx",
            "secret-key: xxxxxxx-xxxx-xxxx-xxxx-xxxxxxxx",
            "application-type: REST"
    })



    @POST("signin")
    void login(@Body LoginRaw raw, Callback<LoginResponse> callback);
}

