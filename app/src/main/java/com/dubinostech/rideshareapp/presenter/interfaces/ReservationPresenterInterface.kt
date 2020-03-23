/**
 * Interface ReservationPresenterInterface to organize the reservation presenter
 * To be implemented by ReservationPresenter
 * Has CallReservation method that takes username and password
 */
package com.dubinostech.rideshareapp.presenter.interfaces

interface ReservationPresenterInterface {

    fun callReservation(tripID: String, passengers: Integer)

}
