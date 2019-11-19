package com.dubinostech.rideshareapp.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GatewayAPIInterface {

    String LOGIN = "signin";
    String SIGNUP = "signup";

    @POST(LOGIN)
    Call<LoginResponse> login(@Body LoginRaw loginCall);

    @POST(SIGNUP)
    Call<SignupResponse> signup(@Body SignupRaw signUpCall);
}

