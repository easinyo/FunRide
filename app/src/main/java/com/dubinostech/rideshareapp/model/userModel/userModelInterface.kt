package com.dubinostech.rideshareapp.model.userModel

import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode
import com.dubinostech.rideshareapp.repository.Api.Responses.UserInfoResponse
import com.dubinostech.rideshareapp.repository.Data.User


interface UserInfoCallback {


    fun signUpOrUpdate(
        user: User,
        code: Integer,
        validationErrorListener: IValidationErrorListener,
        signUpFinishedListener: UserInfoCallback.IOnSignUpFinishedListener
    )

    interface IOnSignUpFinishedListener {

        fun getUserData(user: UserInfoResponse)
        fun errorMsg(errorMsg: String)
    }

    interface IValidationErrorListener {

        fun emailError(code: ErrorCode)
        fun passwordError(code: ErrorCode)
    }
}
