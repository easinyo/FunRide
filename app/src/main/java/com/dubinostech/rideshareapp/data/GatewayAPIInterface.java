package com.dubinostech.rideshareapp.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GatewayAPIInterface {

    static final String LOGIN = "signin";

    @POST(LOGIN)
    Call<LoginResponse> login(@Body LoginRaw loginCall);
}

