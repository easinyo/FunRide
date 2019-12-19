/**
Interface LoginView
To be implemented by LoginActivity
*/
package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.data.ErrorCode;
import com.dubinostech.rideshareapp.data.LoginResponse;

public interface LoginView {

    void showLoading();
    void hideLoading();
    void setEmailError(ErrorCode code);
    void setPasswordError(ErrorCode code);
    void loginSuccess(LoginResponse user);
    void loginFailure(ErrorCode code);
    void loginFailure(String errMsg);
}
