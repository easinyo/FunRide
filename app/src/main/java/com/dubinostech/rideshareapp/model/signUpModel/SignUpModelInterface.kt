package com.dubinostech.rideshareapp.model.signUpModel

import com.dubinostech.rideshareapp.data.ErrorCode
import com.dubinostech.rideshareapp.data.SignupResponse
import com.dubinostech.rideshareapp.data.User


interface SignUpCallback {


    fun signUp(
        user: User,
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
