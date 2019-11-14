package com.dubinostech.rideshareapp.data

import com.google.gson.annotations.SerializedName

class LoginResponse : BaseResponse() {

    //@SerializedName("name")
    var name: String? = null

    @SerializedName("___class")
    var type: String? = null

    @SerializedName("user-token")
    var token: String? = null

    var username: String? = null

    var objectId: String? = null
}
