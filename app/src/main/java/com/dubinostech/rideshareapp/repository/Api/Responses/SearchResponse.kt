package com.dubinostech.rideshareapp.repository.Api.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResponse {

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

    @SerializedName("departure_date")
    @Expose
    private var departure_date: String? = null

    @SerializedName("departure_datetime")
    @Expose
    private var departure_datetime: String? = null


    /**
     *
     * @return
     * The id
     */
    fun getId(): String? {
        return id
    }

    /**
     *
     * @param id
     * The id
     */
    fun setId(id: String) {
        this.id = id
    }

    /**
     *
     * @return
     * The departure_address
     */
    fun getDepartureAddress(): String? {
        return departure_address
    }

    /**
     *
     * @param departure_address
     * The departure_address
     */
    fun setDepartureAddress(departure_address: String) {
        this.departure_address = departure_address
    }

    /**
     *
     * @return
     * The arrival_address
     */
    fun getArrivalAddress(): String? {
        return arrival_address
    }

    /**
     *
     * @param arrival_address
     * The arrival_address
     */
    fun setArrivalAddress(arrival_address: String) {
        this.arrival_address = arrival_address
    }

    /**
     *
     * @return
     * The available_spot
     */
    fun getDavailableSpot(): String? {
        return available_spot
    }

    /**
     *
     * @param available_spot
     * The available_spot
     */
    fun setAvailableSpot(available_spot: String) {
        this.available_spot = available_spot
    }

    /**
     *
     * @return
     * The fare
     */
    fun getFare(): String? {
        return fare
    }

    /**
     *
     * @param fare
     * The fare
     */
    fun setFare(fare: String) {
        this.fare = fare
    }

    /**
     *
     * @return
     * The departure_city
     */
    fun getDepartureCity(): String? {
        return departure_city
    }

    /**
     *
     * @param departure_province
     * The departure_province
     */
    fun setDepartureProvince(departure_province: String) {
        this.departure_province = departure_province
    }

    /**
     *
     * @return
     * The arrival_city
     */
    fun getArrivalCity(): String? {
        return arrival_city
    }

    /**
     *
     * @param arrival_city
     * The arrival_city
     */
    fun setArrivalCity(arrival_city: String) {
        this.arrival_city = arrival_city
    }

    /**
     *
     * @return
     * The arrival_province
     */
    fun getArrivalProvince(): String? {
        return arrival_province
    }

    /**
     *
     * @param arrival_province
     * The arrival_province
     */
    fun setArrivalProvince(arrival_province: String) {
        this.arrival_province = arrival_province
    }

    /**
     *
     * @return
     * The departure_date
     */
    fun getDepartureDate(): String? {
        return departure_date
    }

    /**
     *
     * @param departure_date
     * The departure_date
     */
    fun setDepartureDateTime(departure_date: String) {
        this.departure_date = departure_date
    }

    /**
     *
     * @return
     * The departure_datetime
     */
    fun getDepartureTime(): String? {
        return departure_datetime
    }

    /**
     *
     * @param departure_datetime
     * The departure_datetime
     */
    fun setDepartureTime(departure_datetime: String) {
        this.departure_datetime = departure_datetime
    }

}
