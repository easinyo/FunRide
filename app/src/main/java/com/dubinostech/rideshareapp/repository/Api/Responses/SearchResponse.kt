package com.dubinostech.rideshareapp.repository.Api.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResponse {

    var rides: List<RidesResponse>? = null
}

class RidesResponse {

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("departure_address")
    @Expose
    private var departure_address: String? = null

    @SerializedName("arrival_address")
    @Expose
    private var arrival_address: String? = null

    @SerializedName("available_spot")
    @Expose
    private var available_spot: String? = null

    @SerializedName("fare")
    @Expose
    private var fare: String? = null

    @SerializedName("departure_city")
    @Expose
    private var departure_city: String? = null

    @SerializedName("departure_province")
    @Expose
    private var departure_province: String? = null

    @SerializedName("arrival_city")
    @Expose
    private var arrival_city: String? = null

    @SerializedName("arrival_province")
    @Expose
    private var arrival_province: String? = null

    @SerializedName("departure_datetime")
    @Expose
    private var departure_datetime: String? = null

}
