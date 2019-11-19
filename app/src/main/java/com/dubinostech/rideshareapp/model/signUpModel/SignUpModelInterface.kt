package com.dubinostech.rideshareapp.model.signUpModel

import com.dubinostech.rideshareapp.data.ErrorCode
import com.dubinostech.rideshareapp.data.SignupResponse


interface SignUpCallback {


    fun signUp(
        email: String,
        passWord: String,
        confirmedPassword: String,
        validationErrorListener: IValidationErrorListener,
        signUpFinishedListener: SignUpCallback.IOnSignUpFinishedListener
    )

    interface IOnSignUpFinishedListener {

        fun getUserData(user: SignupResponse)
        fun errorMsg(errorMsg: String)
    }

    interface IValidationErrorListener {

        fun emailError(code: ErrorCode)
        fun passwordError(code: ErrorCode)
    }
}
