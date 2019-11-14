package com.dubinostech.rideshareapp.model.loginModel

import com.dubinostech.rideshareapp.data.LoginResponse
import retrofit.RetrofitError
import retrofit.client.Response

interface LogInCallback {

    fun onResponse(loginResponse: LoginResponse, response: Response)

    fun onError(retrofitError: RetrofitError)
    fun onError(retrofitError: RetrofitError, json: String)

    fun onNetworkConnectionError()

    fun onServerError(message: String)
}
