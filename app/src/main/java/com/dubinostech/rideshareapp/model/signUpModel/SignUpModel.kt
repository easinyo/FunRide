package com.dubinostech.rideshareapp.model.SignModel

import com.dubinostech.rideshareapp.data.*
import com.dubinostech.rideshareapp.model.signUpModel.SignUpCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpModel : SignUpCallback {
    private val TAG = "SignupModel"

    private var gatewayAPI: GatewayAPI? = null


    override fun signUp(
        user: User,
        validationErrorListener: SignUpCallback.IValidationErrorListener,
        signUpFinishedListener: SignUpCallback.IOnSignUpFinishedListener
    ) {
        if (isDataValid(user.getEmail(), user.getPassword(), user.getConfirmPassword(), validationErrorListener)) {

            gatewayAPI = GatewayAPI(null)
            val signupRaw = SignupRaw(user.firstName, user.lastName, user.phone, user.email, user.password, user.confirmPassword)

            val responseSignUpCallback = gatewayAPI!!.signup(signupRaw)


            responseSignUpCallback.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {

                    if (response.body() != null && response.isSuccess) {
                        if (response.code() == 202)
                            signUpFinishedListener.errorMsg("Email or Password is wrong !! Try again later.")
                        else signUpFinishedListener.getUserData(response.body())
                    } else {

                        if (response.errorBody() != null) {
                            val error = WebErrorUtils.parseError(response)
                            error?.message?.let { signUpFinishedListener.errorMsg(it) }
                        } else {
                            signUpFinishedListener.errorMsg("Problem getting user !! Try again later.")
                        }
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    signUpFinishedListener.errorMsg("Problem getting user !! Try again later.")
                }
            })
        }
    }


    private fun isDataValid(
        email: String,
        password: String,
        confirmedpassWord: String,
        validationErrorListener: SignUpCallback.IValidationErrorListener
    ): Boolean {

        if (!Utils.isValidEmail(email)) {

            validationErrorListener.emailError(ErrorCode.EMAIL_INVALID)
            return false

        } else if (password.length < 6) {

            validationErrorListener.passwordError(ErrorCode.PASSWORD_INVALID)
            return false

        } else if (confirmedpassWord != password) {
            validationErrorListener.passwordError(ErrorCode.PASSWORD_DONT_MATCH)
            return false

        } else {
            return true
        }
    }
}