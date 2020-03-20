package com.dubinostech.rideshareapp.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.loginModel.ReservationModel;
import com.dubinostech.rideshareapp.presenter.ReservationPresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.ReservationResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.view.ReservationView;


public class ReservationActivity extends AppCompatActivity implements View.OnClickListener, ReservationView {
    private Button makeReservation;
    private TextView departure_city, arrival_city, departure_date, departure_time, price;
    private ProgressDialog progressDialog;
    private String tripID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        makeReservation = findViewById(R.id.reservationBtn);
        departure_city = findViewById(R.id.departure_city);
        arrival_city = findViewById(R.id.arrival_city);
        departure_date = findViewById(R.id.departure_date);
        departure_time = findViewById(R.id.departure_time_label);
        price = findViewById(R.id.price_label);
        makeReservation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reservationBtn) {
            makeReservation();
        }
    }

    private void makeReservation() {
        ReservationPresenter presenter = new ReservationPresenter(this, new ReservationModel());
        presenter.callReservation(tripID);
    }


    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (progressDialog != null)
            progressDialog.setTitle("Reservation");
        progressDialog.setMessage(String.valueOf(R.string.activity_loading_msg));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void reservationSuccess(ReservationResponse reservationResponse) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void reservationFailure(ErrorCode code) {
        // to determine code error
        if (code.getId() == 0) {
            Toast.makeText(
                    this,
                    "",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void reservationFailure(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }
}