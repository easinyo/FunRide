/**
 * The class LoginPresenter is the presenter of the login view. 
 * It implements the interface LoginPresenterInterface
 * It has a method callLogin which is called by the view when an existing user tries to login. 
 * It then updates login model and view.
 */
package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Api.Responses.LoginResponse;
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

    /**
     * callLogin is a helper method that handles the user login
     * @param username
     *            String representing the user's username or email
     * @param password
     *            String representing the user's password
     * It verifies the constraints for loging in and sets the Errors if needed
     * Then notifies the login view
     */
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
                loginView.loginFailure(errorMsg);
            }

        });
    }
}

