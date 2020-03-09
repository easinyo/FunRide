package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;

public interface PostView {
    void showLoading();

    void hideLoading();

    void postSuccess(PostResponse post);

    void postFailure(ErrorCode code);

    void postFailure(String errMsg);
}

