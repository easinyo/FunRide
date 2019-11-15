package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.data.ErrorCode;
import com.dubinostech.rideshareapp.data.LoginResponse;
import com.dubinostech.rideshareapp.model.loginModel.LogInCallback;
import com.dubinostech.rideshareapp.presenter.interfaces.LoginPresenterInterface;
import com.dubinostech.rideshareapp.ui.view.LoginView;

public class LoginPresenter implements LoginPresenterInterface {


    LoginView loginView;
    LogInCallback logInCallback;

    public LoginPresenter(LoginView loginView, LogInCallback logInCallback) {
        this.loginView = loginView;
        this.logInCallback = logInCallback;
    }


    @Override
    public void callLogin(String username, String password) {
        loginView.showLoading();
        logInCallback.login(username, password, new LogInCallback.IValidationErrorListener() {
            @Override public void emailError(ErrorCode code) {
                loginView.hideLoading();
                loginView.setEmailError(code);
            }

            @Override public void passwordError(ErrorCode code) {
                loginView.hideLoading();
                loginView.setPasswordError(code);
            }

        }, new LogInCallback.IOnLoginFinishedListener() {
            @Override public void getUserData(LoginResponse user) {
                loginView.hideLoading();
                if (user != null) {
                    loginView.loginSuccess(user);
                } else {
                    loginView.loginFailure(ErrorCode.LOGIN_FAILED);
                }
            }

            @Override public void errorMsg(String errorMsg) {
                loginView.hideLoading();
                loginView.loginFailure(errorMsg + "------------");
            }

        });
    }
}

