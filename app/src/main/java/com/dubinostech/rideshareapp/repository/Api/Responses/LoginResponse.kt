package com.dubinostech.rideshareapp.repository.Api.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse{

    @SerializedName("auth_token")
    @Expose
    var auth_token: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("firstname")
    @Expose
    var firstname: String? = null

    @SerializedName("lastname")
    @Expose
    var lastname: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

}
