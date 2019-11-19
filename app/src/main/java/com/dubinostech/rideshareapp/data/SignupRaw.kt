package com.dubinostech.rideshareapp.data

class SignupRaw(eUserName: String, passWord: String, confirmePassWord: String) {

    private var email: String? = eUserName
    private var password: String? = passWord
    private var passwordConfirmation: String? = confirmePassWord
}