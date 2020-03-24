/**
Interface SignUpView
To be implemented by SignUpActivity
*/
package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Api.Responses.SignupResponse;

public interface SignUpView {
    void showLoading();

    void hideLoading();

    void setEmailError(ErrorCode code);

    void setPasswordError(ErrorCode code);

    void signUpSuccess(SignupResponse user);

    void signUpFailure(ErrorCode code);

    void signUpFailure(String errMsg);
}
