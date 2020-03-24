package com.dubinostech.rideshareapp.repository.Api.Raws

class UserInfoRaw(eFirstName: String, eLastName: String, ePhone: String, eEmail: String, passWord: String, confirmePassWord: String) {

    private var firstname: String? = eFirstName
    private var lastname: String? = eLastName
    private var phone: String? = ePhone
    private var email: String? = eEmail
    private var password: String? = passWord
    private var passwordConfirmation: String? = confirmePassWord
}