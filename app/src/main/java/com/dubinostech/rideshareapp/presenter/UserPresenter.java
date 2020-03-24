/**
 * The class UserPresenter is the presenter of the signup feature.
 * It implements the interface UserPresenterInterface
 * It has a method callSignup which is called by the view when a new user registers. 
 * It then updates sign up model and view.
 */
package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Api.Responses.UserInfoResponse;
import com.dubinostech.rideshareapp.repository.Data.User;
import com.dubinostech.rideshareapp.model.userModel.UserInfoCallback;
import com.dubinostech.rideshareapp.presenter.interfaces.UserPresenterInterface;
import com.dubinostech.rideshareapp.ui.view.UserInfoView;

import org.jetbrains.annotations.NotNull;


public class UserPresenter implements UserPresenterInterface {

    UserInfoView signupView;
    UserInfoCallback signupCallback;

    public UserPresenter(UserInfoView signupView, UserInfoCallback signupCallback) {
        this.signupView = signupView;
        this.signupCallback = signupCallback;
    }

    /**
     * callSignup is a helper method registers a new user.
     * @param user
     *            Object user containing first name, last name, email, phone and password
     * It verifies the constraints for registration and sets the errors if needed
     * Then it notifies the signup view
     */
    @Override
    public void callUserSignUpOrUpdate(User user, int code) {
        signupCallback.signUpOrUpdate(user, new Integer(code), new UserInfoCallback.IValidationErrorListener() {
            @Override
            public void emailError(ErrorCode code) {
                signupView.hideLoading();
                signupView.setEmailError(code);
            }

            @Override
            public void passwordError(ErrorCode code) {
                signupView.hideLoading();
                signupView.setPasswordError(code);
            }

        }, new UserInfoCallback.IOnSignUpFinishedListener() {
            @Override
            public void getUserData(@NotNull UserInfoResponse user) {
                signupView.hideLoading();
                if (user != null) {
                    signupView.onSuccess(user);
                } else {
                    signupView.onFailure(ErrorCode.SIGNUP_FAILED);
                }
            }

            @Override
            public void errorMsg(String errorMsg) {
                signupView.hideLoading();
                signupView.onFailure(errorMsg);
            }

        });
    }
}

