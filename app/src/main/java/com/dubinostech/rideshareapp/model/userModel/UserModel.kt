package com.dubinostech.rideshareapp.model.userModel

import com.dubinostech.rideshareapp.repository.Api.GatewayAPI
import com.dubinostech.rideshareapp.repository.Api.Raws.UserInfoRaw
import com.dubinostech.rideshareapp.repository.Api.Responses.UserInfoResponse
import com.dubinostech.rideshareapp.repository.Data.User
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode
import com.dubinostech.rideshareapp.repository.ErrorHandler.WebErrorUtils
import com.dubinostech.rideshareapp.repository.Libraries.Utils
import com.dubinostech.rideshareapp.repository.Data.LoggedUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserModel : UserInfoCallback {
    private val TAG = "UserModel"

    private var gatewayAPI: GatewayAPI? = null


    override fun signUpOrUpdate(
        user: User,
        code: Integer,
        validationErrorListener: UserInfoCallback.IValidationErrorListener,
        signUpFinishedListener: UserInfoCallback.IOnSignUpFinishedListener
    ) {
        if (isDataValid(
                user.getEmail(),
                user.getPassword(),
                user.getConfirmPassword(),
                code,
                validationErrorListener
            )
        ) {

            gatewayAPI = GatewayAPI(null)
            val userInfoRaw = UserInfoRaw(
                user.firstName,
                user.lastName,
                user.phone,
                user.email,
                user.password,
                user.confirmPassword
            )

            val token = LoggedUser.token

            val responseUserInfoCallback =
                if (code.equals(Integer(Utils.SIGNUP)))
                    gatewayAPI!!.signup(userInfoRaw)
                else gatewayAPI!!.editProfile(token, userInfoRaw)

            responseUserInfoCallback.enqueue(object : Callback<UserInfoResponse> {
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {

                    if (response.body() != null && response.isSuccess) {
                        if (response.code() == 202)
                            signUpFinishedListener.errorMsg("Something went wrong !! Try again later.")
                        else if (code.equals(Integer(Utils.SIGNUP)))
                            signUpFinishedListener.getSignUpResponse(response.body())
                        else
                            signUpFinishedListener.getUpdateResponse(response.body())
                    } else {

                        if (response.errorBody() != null) {
                            val error = WebErrorUtils.parseError(response)
                            error?.message?.let { signUpFinishedListener.errorMsg(it) }
                        } else {
                            signUpFinishedListener.errorMsg("Problem getting user data !! Try again later.")
                        }
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    signUpFinishedListener.errorMsg("Problem getting user !! Try again later.")
                }
            })
        }
    }


    private fun isDataValid(
        email: String,
        password: String,
        confirmedpassWord: String,
        code: Integer,
        validationErrorListener: UserInfoCallback.IValidationErrorListener
    ): Boolean {

        if (!Utils.isValidEmail(email)) {

            validationErrorListener.emailError(ErrorCode.EMAIL_INVALID)
            return false

        }
        if (!Utils.isValidEmail(email)) {

            validationErrorListener.emailError(ErrorCode.EMAIL_INVALID)
            return false

        } else if (code.equals(Integer(Utils.SIGNUP))) {
            if (password.length < 6) {
                validationErrorListener.passwordError(ErrorCode.PASSWORD_INVALID)
                return false

            } else if (confirmedpassWord != password) {
                validationErrorListener.passwordError(ErrorCode.PASSWORD_DONT_MATCH)
                return false

            } else {
                return true
            }
        } else {
            return true
        }
    }
}