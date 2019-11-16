package com.dubinostech.rideshareapp.model.loginModel

import com.dubinostech.rideshareapp.data.ErrorCode
import com.dubinostech.rideshareapp.data.LoginResponse


interface LogInCallback {


    fun login(
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
