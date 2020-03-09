package com.dubinostech.rideshareapp.repository.Api;

import com.dubinostech.rideshareapp.repository.Api.Raws.LoginRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.SearchRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.SignupRaw;
import com.dubinostech.rideshareapp.repository.Api.Responses.LoginResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * A gateway Interface that record all the function calls
 */
public interface GatewayAPIInterface {

    String LOGIN = "signin";
    String SIGNUP = "signup";
    String SEARCH = "search_trips";


    @POST(LOGIN)
    Call<LoginResponse> login(@Body LoginRaw loginCall);

    @POST(SIGNUP)
    Call<SignupResponse> signup(@Body SignupRaw signUpCall);

    @POST(SEARCH)
    Call<SearchResponse> search(@Body SearchRaw searchCall);
}

