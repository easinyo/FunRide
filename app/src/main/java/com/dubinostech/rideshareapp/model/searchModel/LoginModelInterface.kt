package com.dubinostech.rideshareapp.model.loginModel

import com.dubinostech.rideshareapp.repository.Api.Responses.LoginResponse
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode


interface SearchCallback {


    fun search(
        userName: String,
        passWord: String,
        validationErrorListener: IValidationErrorListener,
        loginFinishedListener: IOnLoginFinishedListener
    )

    interface IOnLoginFinishedListener {

        fun getUserData(user: LoginResponse)

        fun errorMsg(errorMsg: String)
    }

    interface IValidationErrorListener {

        fun emailError(code: ErrorCode)

        fun passwordError(code: ErrorCode)
    }
}
