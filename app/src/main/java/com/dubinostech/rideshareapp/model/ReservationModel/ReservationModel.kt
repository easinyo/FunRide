package com.dubinostech.rideshareapp.model.loginModel

import android.util.Log
import com.dubinostech.rideshareapp.repository.Api.GatewayAPI
import com.dubinostech.rideshareapp.repository.Api.Raws.ReservationRaw
import com.dubinostech.rideshareapp.repository.Api.Responses.ReservationResponse
import com.dubinostech.rideshareapp.repository.Data.LoggedUser
import com.dubinostech.rideshareapp.repository.ErrorHandler.WebErrorUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReservationModel : ReservationCallback {
    private val TAG = "ReservationModel"

    private var gatewayAPI: GatewayAPI? = null

    override fun makeReservation(
        tripID: String,
        passengers: Integer,
        reservationFinishedListener: ReservationCallback.IOnReservationFinishedListener
    ) {
        gatewayAPI = GatewayAPI(null)

        Log.d("presenterBooking", "id: $tripID spots: $passengers")

        val reservationRaw =
            ReservationRaw(tripID, passengers)
        val token =
            if (LoggedUser.getToken() != null) "Bearer " + LoggedUser.getToken() else "Bearer "

        val responseLoginCallback = gatewayAPI!!.makeReservation(token, reservationRaw)

        responseLoginCallback.enqueue(object : Callback<ReservationResponse> {
            override fun onResponse(
                call: Call<ReservationResponse>,
                response: Response<ReservationResponse>
            ) {

                if (response.body() != null && response.isSuccess) {
                    if (response.code() == 202)
                        reservationFinishedListener.errorMsg("Sold out !!.")
                    else reservationFinishedListener.getReservationData(response.body())
                } else {

                    if (response.errorBody() != null) {
                        val error = WebErrorUtils.parseError(response)
                        error?.message?.let { reservationFinishedListener.errorMsg(it) }
                    } else {
                        reservationFinishedListener.errorMsg("Something went wrong! Try again later.")
                    }
                }
            }

            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                reservationFinishedListener.errorMsg("Something went wrong! Try again later.")
            }
        })
    }
}