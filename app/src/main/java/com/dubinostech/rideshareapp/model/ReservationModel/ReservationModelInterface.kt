package com.dubinostech.rideshareapp.model.loginModel

import com.dubinostech.rideshareapp.repository.Api.Responses.ReservationResponse


interface ReservationCallback {


    fun makeReservation(
        tripID: String,
        reservationFinishedListener: IOnReservationFinishedListener
    )

    interface IOnReservationFinishedListener {

        fun getReservationData(reservation: ReservationResponse)

        fun errorMsg(errorMsg: String)
    }

}
