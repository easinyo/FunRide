package com.dubinostech.rideshareapp.repository.Api.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReservationResponse {

    @SerializedName("auth_token")
    @Expose
    var auth_token: String? = null

    @SerializedName("full_name")
    @Expose
    var trip_id: String? = null

}
