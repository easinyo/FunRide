package com.dubinostech.rideshareapp.model.loginModel

import android.text.TextUtils
import com.dubinostech.rideshareapp.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginModel: LogInCallback {
    private val TAG = "LoginModel"


    private var gatewayAPI: GatewayAPI? = null


    override fun login(
        userName: String,
        passWord: String,
        validationErrorListener: LogInCallback.IValidationErrorListener,
        loginFinishedListener: LogInCallback.IOnLoginFinishedListener
    ) {
        if (isDataValid(userName, passWord, validationErrorListener)) {

            gatewayAPI = GatewayAPI(null)
            val loginRaw = LoginRaw(userName, passWord)

            val responseLoginCallback = gatewayAPI!!.login(loginRaw)


            responseLoginCallback.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    if (response.body() != null && response.isSuccess) {
                        if ( response.code()== 202)
                            loginFinishedListener.errorMsg("Email or Password is wrong !! Try again later.")
                        else loginFinishedListener.getUserData(response.body())
                    } else {

                        if (response.errorBody() != null) {
                            val error = WebErrorUtils.parseError(response)
                            error?.message?.let { loginFinishedListener.errorMsg(it) }
                        } else {
                            loginFinishedListener.errorMsg("Problem getting user !! Try again later.")
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginFinishedListener.errorMsg("Problem getting user !! Try again later.")
                }
            })
        }
    }


    private fun isDataValid(
        userName: String,
        password: String,
        validationErrorListener: LogInCallback.IValidationErrorListener
    ): Boolean {

        if (TextUtils.isEmpty(userName)) {

            validationErrorListener.emailError(ErrorCode.ENTER_EMAIL)
            return false

        } else if (!Utils.isValidEmail(userName)) {

            validationErrorListener.emailError(ErrorCode.EMAIL_INVALID)
            return false

        } else if (TextUtils.isEmpty(password)) {

            validationErrorListener.passwordError(ErrorCode.ENTER_PASSWORD)
            return false

        } else if (password.length < 6) {

            validationErrorListener.passwordError(ErrorCode.PASSWORD_INVALID)
            return false

        } else {
            return true
        }
    }
}