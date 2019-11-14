package com.dubinostech.rideshareapp.model.loginModel

import android.util.Log
import com.dubinostech.rideshareapp.data.GatewayAPI
import com.dubinostech.rideshareapp.data.LoginRaw
import com.dubinostech.rideshareapp.data.LoginResponse
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import retrofit.mime.TypedByteArray


class LoginModel {
    private val TAG = "LogInInteractor"


    fun login(logInRaw: LoginRaw, logInCallback: LogInCallback) {

        GatewayAPI.getMyApiClient().
            login(logInRaw, object : Callback<LoginResponse> {
                    override fun success(loginResponse: LoginResponse, response: Response) {
                        logInCallback.onResponse(loginResponse, response)
                    }

                    override fun failure(error: RetrofitError) {
                        var json: String? = null
                        try {
                            json = String((error.response.body as TypedByteArray).bytes)
                        } catch (e: NullPointerException) {
                        }

                        Log.v(TAG, "json >>>> " + json!!)
                        if (json != null) {
                            logInCallback.onError(error, json)
                        } else {
                            error.message?.let { logInCallback.onServerError(it) }
                        }
                    }
        })
    }
}