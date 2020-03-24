/**
Interface UserInfoView
To be implemented by SignUpActivity
*/
package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Api.Responses.UserInfoResponse;

public interface UserInfoView {
    void showLoading();

    void hideLoading();

    void setEmailError(ErrorCode code);

    void setPasswordError(ErrorCode code);

    void onSuccess(UserInfoResponse user);

    void onFailure(ErrorCode code);

    void onFailure(String errMsg);
}
