/**
 * The class SignupPresenter is the presenter of the signup feature. 
 * It implements the interface SignUpPresenterInterface
 * It has a method callSignup which is called by the view when a new user registers. 
 * It then updates sign up model and view.
 */
package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Api.Responses.SignupResponse;
import com.dubinostech.rideshareapp.repository.Data.User;
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

    /**
     * callSignup is a helper method registers a new user.
     * @param user
     *            Object user containing first name, last name, email, phone and password
     * It verifies the constraints for registration and sets the errors if needed
     * Then it notifies the signup view
     */
    @Override
    public void callSignUp(User user) {
        signupCallback.signUp(user, new SignUpCallback.IValidationErrorListener() {
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

