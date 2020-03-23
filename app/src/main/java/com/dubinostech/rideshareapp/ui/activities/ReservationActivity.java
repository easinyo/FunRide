package com.dubinostech.rideshareapp.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.loginModel.ReservationModel;
import com.dubinostech.rideshareapp.presenter.ReservationPresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.ReservationResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Libraries.Utils;
import com.dubinostech.rideshareapp.ui.view.ReservationView;


public class ReservationActivity extends AppCompatActivity implements ReservationView {
    private final String TAG = "TAG_ReservationActivity";

    private Button makeReservation;
    private TextView departure_city, arrival_city, departure_date, departure_time, price, noPassengers;
    private ProgressDialog progressDialog;
    private String tripID;
    private int spots = 0;
    private int availableSpots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        makeReservation = findViewById(R.id.reservationBtn);
        departure_city = findViewById(R.id.departure_city);
        arrival_city = findViewById(R.id.arrival_city);
        departure_date = findViewById(R.id.departure_date);
        departure_time = findViewById(R.id.departure_time_label);
        noPassengers = findViewById(R.id.passengers);
        price = findViewById(R.id.price_label);

        this.progressDialog = new ProgressDialog(this);

        departure_city.setText(getIntent().getExtras().getString("departure_city") + "\n" + getIntent().getExtras().getString("departure_address"));
        arrival_city.setText(getIntent().getExtras().getString("arrival_city") + "\n" + getIntent().getExtras().getString("arrival_address"));
        departure_date.setText(getIntent().getExtras().getString("departure_date"));
        departure_time.setText(getIntent().getExtras().getString("departure_time"));
        price.setText(getIntent().getExtras().getString("cost"));
        tripID = getIntent().getExtras().getString("tripID");
        availableSpots = Integer.parseInt(getIntent().getExtras().getString("available_spots"));

        noPassengers.setOnClickListener(v ->{
            final NumberPicker picker = new NumberPicker(this);
            picker.setMinValue(0);
            picker.setMaxValue(availableSpots);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Passengers")
                    .setPositiveButton("OK", (dialog, which) -> {
                        noPassengers.setText(String.valueOf(picker.getValue()));
                        spots = Integer.parseInt(String.valueOf((picker.getValue())));
                    })
                    .setNegativeButton("CANCEL", (dialog, which) -> {
                    });
            builder.setView(picker);
            builder.create().show();
        });
        makeReservation.setOnClickListener(v->{
            Log.d(TAG, "Booking made");
            ReservationPresenter presenter = new ReservationPresenter(this, new ReservationModel());
            presenter.callReservation(tripID, new Integer(spots));
        });
    }

    @Override
    public void showLoading() {
        if (progressDialog != null)
            progressDialog.setTitle("Reservation");
        progressDialog.setMessage(this.getString(R.string.activity_loading_msg));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void reservationSuccess(ReservationResponse reservationResponse) {
        hideLoading();
        Utils.displayAlertDialogWithCounter(this, this.getString(R.string.activity_reservation_success));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void reservationFailure(ErrorCode code) {
        // to determine code error
        if (code.getId() == 0) {
            Utils.displayAlertDialogWithCounter(this, code.toString());

            Toast.makeText(
                    this,
                    "",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void reservationFailure(String errorMsg) {
        Utils.displayAlertDialogWithCounter(this, errorMsg);
    }
}