/**
 * Interface ReservationView
 * To be implemented by ReservationActivity
 */
package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.repository.Api.Responses.ReservationResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;

public interface ReservationView {
    void showLoading();

    void hideLoading();

    void reservationSuccess(ReservationResponse reservation);

    void reservationFailure(ErrorCode code);

    void reservationFailure(String errorMsg);


}
