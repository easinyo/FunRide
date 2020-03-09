package com.dubinostech.rideshareapp.model.PostFragmentModel;

import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.Data.PostData;

public interface PostModelInterface {

    void postRide(PostData postData, PostModelInterface.IOnPostFinishedListener postFinishedListener);

    interface IOnPostFinishedListener {

        void getPostData(PostResponse postResponse);
        void errorMsg(String errorMsg);
    }
}