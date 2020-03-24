package com.dubinostech.rideshareapp.repository.Api;

import com.dubinostech.rideshareapp.repository.Api.Raws.LoginRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.PostRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.ReservationRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.SearchRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.UserInfoRaw;
import com.dubinostech.rideshareapp.repository.Api.Responses.LoginResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.ReservationResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.UserInfoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * A gateway Interface that record all the function calls
 */
public interface GatewayAPIInterface {

    String LOGIN = "signin";
    String SIGNUP = "signup";
    String POST_TRIP = "post_trip";
    String SEARCH = "search_trips";
    String BOOK_TRIP = "book_trips";
    String EDIT_PROFILE = "user";

    @POST(LOGIN)
    Call<LoginResponse> login(@Body LoginRaw loginCall);

    @POST(SIGNUP)
    Call<UserInfoResponse> signup(@Body UserInfoRaw signUpCall);

    @POST(EDIT_PROFILE)
    Call<UserInfoResponse> editProfile(@Header("Authorization") String token, @Body UserInfoRaw signUpCall);

    @POST(POST_TRIP)
    Call<PostResponse> postRide(@Header("Authorization") String token,@Body PostRaw postCall);

    @POST(SEARCH)
    Call<List<SearchResponse>> search(@Body SearchRaw searchCall);

    @POST(BOOK_TRIP)
    Call<ReservationResponse> reserve(@Header("Authorization") String token, @Body ReservationRaw reservationCall);
}

