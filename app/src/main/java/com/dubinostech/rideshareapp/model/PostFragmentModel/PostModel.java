package com.dubinostech.rideshareapp.model.PostFragmentModel;

import com.dubinostech.rideshareapp.repository.Api.GatewayAPI;
import com.dubinostech.rideshareapp.repository.Api.Raws.PostRaw;
import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.Data.LoggedUser;
import com.dubinostech.rideshareapp.repository.Data.PostData;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ApIError;
import com.dubinostech.rideshareapp.repository.ErrorHandler.WebErrorUtils;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostModel implements PostModelInterface {

    private String TAG = "SignupModel";

    private GatewayAPI gatewayAPI;


    @Override
    public void postRide(PostData postData, IOnPostFinishedListener postFinishedListener) {

            gatewayAPI = new GatewayAPI(null);
            PostRaw postRaw = new PostRaw(
                    postData.getDeparture_city(),
                    postData.getDeparture_address(),
                    postData.getArrival_city(),
                    postData.getArrival_address(),
                    postData.getAvailable_spot(),
                    postData.getFare(),
                    postData.getDeparture_datetime()
            );

            String token = (LoggedUser.token!=null)?("Bearer " + LoggedUser.token): "Bearer ";

            Call<PostResponse> responsePostCallback = gatewayAPI.postRide(token, postRaw);

            responsePostCallback.enqueue(new Callback<PostResponse>(){
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body() != null && response.isSuccess()) {
                        if (response.code() == 202)
                            postFinishedListener.errorMsg("Something went wrong !! Try again later.");
                        else postFinishedListener.getPostData(response.body());
                    } else {

                        if (response.errorBody() != null) {
                            ApIError error = WebErrorUtils.parseError(response);
                            if (error.getMessage() != null){
                                postFinishedListener.errorMsg(error.getMessage());
                            }
                        } else {
                            postFinishedListener.errorMsg("Problem sending the data !! Try again later.");
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                     postFinishedListener.errorMsg("Problem sending the data !! Try again later.");
                }
            });
    }
}