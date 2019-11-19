package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.data.ErrorCode;
import com.dubinostech.rideshareapp.data.SignupResponse;
import com.dubinostech.rideshareapp.model.signUpModel.SignUpCallback;
import com.dubinostech.rideshareapp.presenter.interfaces.SignUpPresenterInterface;
import com.dubinostech.rideshareapp.ui.view.SignUpView;

import org.jetbrains.annotations.NotNull;


public class SignupPresenter implements SignUpPresenterInterface {

    SignUpView signupView;
    SignUpCallback signupCallback;

    public SignupPresenter(SignUpView signupView, SignUpCallback signupCallback) {
        this.signupView = signupView;
        this.signupCallback = signupCallback;
    }


    @Override
    public void callSignUp(String email, String password, String confirmedPassword) {
        signupView.showLoading();
        signupCallback.signUp(email, password, confirmedPassword, new SignUpCallback.IValidationErrorListener() {
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

        }, new SignUpCallback.IOnSignUpFinishedListener() {
            @Override
            public void getUserData(@NotNull SignupResponse user) {
                signupView.hideLoading();
                if (user != null) {
                    signupView.signUpSuccess(user);
                } else {
                    signupView.signUpFailure(ErrorCode.SIGNUP_FAILED);
                }
            }

            @Override
            public void errorMsg(String errorMsg) {
                signupView.hideLoading();
                signupView.signUpFailure(errorMsg);
            }

        });
    }
}

