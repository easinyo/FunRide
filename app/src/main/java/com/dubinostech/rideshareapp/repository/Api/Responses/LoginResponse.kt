package com.dubinostech.rideshareapp.repository.Api.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse{

    @SerializedName("auth_token")
    @Expose
    var auth_token: String? = null

    @SerializedName("full_name")
    @Expose
    var full_name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

}
