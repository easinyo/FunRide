/**
 * The class ReservationPresenter is the presenter of the reservation view.
 * It implements the interface ReservationPresenterInterface
 * It has a method callReservation which is called by the view when an existing user tries to make a reservation in a trip.
 * It then updates reservation model and view.
 */
package com.dubinostech.rideshareapp.presenter;

import android.util.Log;

import com.dubinostech.rideshareapp.model.loginModel.ReservationCallback;
import com.dubinostech.rideshareapp.presenter.interfaces.ReservationPresenterInterface;
import com.dubinostech.rideshareapp.repository.Api.Responses.ReservationResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.view.ReservationView;

import org.jetbrains.annotations.NotNull;

public class ReservationPresenter implements ReservationPresenterInterface {


    ReservationView reservationView;
    ReservationCallback reservationCallback;

    public ReservationPresenter(ReservationView reservationView, ReservationCallback reservationCallback) {
        this.reservationView = reservationView;
        this.reservationCallback = reservationCallback;
    }

    /**
     * callReservation is a helper method that handles the user login
     *
     * @param tripID String representing the trip id
     *               It verifies the constraints for reserving a spot in a trip  and sets the Errors if needed
     *               Then notifies the reservation view
     */
    @Override
    public void callReservation(@NotNull String tripID, @NotNull Integer passengers) {
        reservationView.showLoading();
        Log.d("presenterBooking", "id: "+tripID +" spots: "+passengers);
        reservationCallback.makeReservation(tripID,passengers, new ReservationCallback.IOnReservationFinishedListener() {
            @Override
            public void errorMsg(@NotNull String errorMsg) {
                reservationView.hideLoading();
                reservationView.reservationFailure(errorMsg);
            }

            @Override
            public void getReservationData(@NotNull ReservationResponse reservation) {
                reservationView.hideLoading();
                if (tripID != null) {
                    reservationView.reservationSuccess(reservation);
                } else {
                    reservationView.reservationFailure(ErrorCode.RESERVATION_FAILED);
                }
            }

        });
    }
}

