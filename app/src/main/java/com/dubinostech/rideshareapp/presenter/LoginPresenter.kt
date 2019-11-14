package com.dubinostech.rideshareapp.presenter

import com.dubinostech.rideshareapp.data.BaseResponse
import com.dubinostech.rideshareapp.data.LoginRaw
import com.dubinostech.rideshareapp.data.LoginResponse
import com.dubinostech.rideshareapp.model.loginModel.LogInCallback
import com.dubinostech.rideshareapp.data.LoggedInUser
import com.dubinostech.rideshareapp.model.loginModel.LoginModel
import com.dubinostech.rideshareapp.presenter.interfaces.LoginPresenterInterface
import com.dubinostech.rideshareapp.ui.view.LoginView
import com.google.gson.GsonBuilder
import retrofit.RetrofitError
import retrofit.client.Response

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

class LoginPresenter : LoginPresenterInterface<LoginView>, LogInCallback {

    private var loginView: LoginView? = null
    private var loginModel: LoginModel? = null
    private var username: String? = null
    private var password:String? = null


    fun login(username: String, password: String) {
        this.username = username
        this.password = password

        //setup login to be added to the Gateway
        val logInRaw = LoginRaw()
        logInRaw.login =this.username
        logInRaw.password =this.password


        loginView?.showLoading()
        loginModel?.login(logInRaw, this)

    }

    fun logout() {
        // TODO: revoke authentication
    }

    override fun attachedView(view: LoginView) {
        loginView = view
        loginModel = LoginModel()
    }

    override fun detachView() {
        loginView = null
    }

    override fun onResponse(loginResponse: LoginResponse, response: Response) {
        loginView?.hideLoading()

        if (loginResponse.isSuccess()) {
            val loggedInUser = LoggedInUser()
            loginResponse.username?.let { loggedInUser.setEmail(it) }
            loginResponse.name?.let { loggedInUser.setName(it) }
            loginResponse.objectId?.let { loggedInUser.setObjectId(it) }
            loginResponse.token?.let { loggedInUser.setToken(it) }

            loginView?.onLoginSuccess(loggedInUser)
        } else {
            loginView?.showError(loginResponse.getMessage())
        }

    }

    override fun onError(retrofitError: RetrofitError) {
        loginView?.hideLoading()
    }

    override fun onError(retrofitError: RetrofitError, json: String) {
        loginView?.hideLoading()
        val gson = GsonBuilder().create()
        val baseResponse = gson.fromJson<BaseResponse>(json, BaseResponse::class.java!!)
        if (baseResponse != null) {
            val message = baseResponse!!.getMessage()
            loginView?.showError(message)
        } else {
            loginView?.showError("There is an error")
        }

    }

    override fun onNetworkConnectionError() {
        loginView?.hideLoading()
    }

    override fun onServerError(message: String) {
        loginView?.hideLoading()
    }
}


