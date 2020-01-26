package com.dubinostech.rideshareapp.model.signUpModel

import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode
import com.dubinostech.rideshareapp.repository.Api.Responses.SignupResponse
import com.dubinostech.rideshareapp.repository.Data.User


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
