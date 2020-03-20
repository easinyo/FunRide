package com.dubinostech.rideshareapp.ui.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.PostFragmentModel.PostModel;
import com.dubinostech.rideshareapp.presenter.PostPresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.Data.PostData;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.view.PostView;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A fragment that display the post page
 */
public class PostFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, PostView {

    private final String TAG = "TAG_PostFragment";

    private Button postRide;
    private TextView departureDateLabel;
    private TextView departureTimeLabel;
    private TextView noPassengersLabel;
    private TextView priceLabel;
    private TextView departureDate;
    private TextView departureTime;
    private TextView noPassengers;
    private TextView price;
    private Calendar myCalendar;

    private ProgressDialog progressDialog;


    private double mPrice;
    private int mPassengers;

    private int uiPrice;
    private int uiPassengers;

    PlacesClient placesClient;

    String departure_city;
    String departure_address;
    String arrival_city;
    String arrival_address;

    public PostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup groupView = (ViewGroup)inflater.inflate(R.layout.fragment_post, container, false);

        postRide = groupView.findViewById(R.id.post_ride);

        departureDateLabel = groupView.findViewById(R.id.departure_date_label);
        departureTimeLabel = groupView.findViewById(R.id.departure_time_label);
        noPassengersLabel = groupView.findViewById(R.id.passengers_label);
        priceLabel = groupView.findViewById(R.id.price_label);

        departureDate = groupView.findViewById(R.id.departure_date);
        departureTime = groupView.findViewById(R.id.departure_time);
        noPassengers = groupView.findViewById(R.id.passengers);
        price = groupView.findViewById(R.id.price);

        myCalendar = Calendar.getInstance();

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        departureDate.setText(sdf.format(myCalendar.getTime()));
        departureTime.setText(Calendar.HOUR + ":" + Calendar.MINUTE);

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), "AIzaSyByniyl8kvVAZ0eGBbPxyUVzVg9jgV5XfA");
        }
        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(getActivity());

        final AutocompleteSupportFragment autocompleteSupportFragmentArrival =
                (AutocompleteSupportFragment)
                        getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_arrival_location);

        autocompleteSupportFragmentArrival.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

        autocompleteSupportFragmentArrival.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        arrival_address = place.getName();
                        arrival_city = place.getAddress().replace(arrival_address + ",", "");
                        Log.d(TAG, ""+place.toString() + "arrival_address: "+ arrival_address + "arrival_city: "+ arrival_city);
                    }

                    @Override
                    public void onError(Status status) {
                        Log.d(TAG, ""+status.getStatusMessage());
                    }
                });

        final AutocompleteSupportFragment autocompleteSupportFragmentDeparture =
                (AutocompleteSupportFragment)
                        getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_departure_location);

        autocompleteSupportFragmentDeparture.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

        autocompleteSupportFragmentDeparture.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();
                        departure_address = place.getName();
                        departure_city = place.getAddress().replace(departure_address + ",", "");

                        Log.d(TAG, "departure_address:  "+ departure_address + " arrival_city: "+ departure_city);
                    }

                    @Override
                    public void onError(Status status) {
                        Log.d(TAG, ""+status.getStatusMessage());
                    }
                });

        departureDateLabel.setOnClickListener(this);
        departureDate.setOnClickListener(this);
        departureTimeLabel.setOnClickListener(this);
        departureTime.setOnClickListener(this);
        noPassengersLabel.setOnClickListener(this);
        noPassengers.setOnClickListener(this);
        priceLabel.setOnClickListener(this);
        price.setOnClickListener(this);
        postRide.setOnClickListener(this);
        // Set to False and enable it when nothing is False
        postRide.setEnabled(true);

        return groupView;
    }

    private String getCityNameByCoordinates(double lat, double lon) throws IOException {
        Geocoder mGeocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 1);

        if (addresses != null && addresses.size() > 0) {
            return addresses.get(0).getLocality();
        }
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        //Potential Call from Presenter
       if(v == departureDateLabel || v ==  departureDate){
            DatePickerDialog dialog =  new DatePickerDialog(getContext(), this, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();

        }else if(v == departureTimeLabel || v == departureTime){
            TimePickerDialog dialog = new TimePickerDialog(getContext(), this, myCalendar.get(Calendar.HOUR),
                    myCalendar.get(Calendar.MINUTE), false);
            dialog.show();
        }else if(v == noPassengersLabel || v == noPassengers){
            final NumberPicker picker = new NumberPicker(getActivity());
            picker.setMinValue(1);
            picker.setMaxValue(10);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Passengers")
                    .setPositiveButton("OK", (dialog, which) -> {
                        mPassengers = picker.getValue();
                        noPassengers.setText(String.valueOf(picker.getValue()));
                        uiPassengers = picker.getValue();
                    })
                    .setNegativeButton("CANCEL", (dialog, which) -> {
                    });
            builder.setView(picker);
            builder.create().show();
        }else if(v == priceLabel || v == price){
            final NumberPicker picker = new NumberPicker(getActivity());
            picker.setMinValue(1);
            picker.setMaxValue(1000);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Price $")
                    .setPositiveButton("OK", (dialog, which) -> {
                        mPrice = picker.getValue();
                        price.setText("$" + picker.getValue());
                        postRide.setEnabled(true);
                        uiPrice = picker.getValue();
                    })
                    .setNegativeButton("CANCEL", (dialog, which) -> {

                    });
            builder.setView(picker);
            builder.create().show();
        }else if(v == postRide){

             float fare = uiPrice;
             int available_spot = uiPassengers;

             String departure_datetime = getLocalDateTime();


            PostData postData=new PostData(departure_city, departure_address, arrival_city, arrival_address,
            fare, available_spot, departure_datetime);

            //Show Dialog

           this.progressDialog = new ProgressDialog(getContext());

            //Sending data to Gateway from here
            PostPresenter presenter = new PostPresenter(this, new PostModel());
            presenter.callPostRide(postData);
        }
    }

    private String getLocalDateTime(){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",  Locale.US);
        String output = parser.format(myCalendar.getTime());

        Log.d("Date" + " LocalDateTime" , output);

        return output;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Log.d(TAG + "Date", String.valueOf(myCalendar.getTime()));
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        departureDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        Log.d(TAG + "Date", String.valueOf(myCalendar.getTime()));
        departureTime.setText(hourOfDay + ": " + minute);
    }

    @Override
    public void showLoading() {
        if (progressDialog != null)
            progressDialog.setTitle(null);
        progressDialog.setMessage(String.valueOf(R.string.activity_loading_msg));
        progressDialog.show();

        ProgressDialog dialog = ProgressDialog.show(getContext(), "Heyyy Loading...", "Please wait...", true);
        dialog.show();
    }
    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void postSuccess(PostResponse post) {
        hideLoading();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                .setMessage(
                        "Your Trip has been posted successfully");

        final AlertDialog alert = dialog.create();
        alert.show();

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }

    @Override
    public void postFailure(ErrorCode code) {
        if (code.getId() == 7) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                    .setMessage( String.valueOf(R.string.activity_post_error));

            final AlertDialog alert = dialog.create();
            alert.show();

            new CountDownTimer(3000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) { }

                @Override
                public void onFinish() {
                    alert.dismiss();
                }
            }.start();
        }
    }

    @Override
    public void postFailure(String errMsg) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                .setMessage( errMsg );

        final AlertDialog alert = dialog.create();
        alert.show();

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }
}