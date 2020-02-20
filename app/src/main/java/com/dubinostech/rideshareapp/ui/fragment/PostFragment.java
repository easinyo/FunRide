package com.dubinostech.rideshareapp.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.dubinostech.rideshareapp.model.PostFragmentModel.PostModel;
import com.dubinostech.rideshareapp.presenter.PostPresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.Data.PostData;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.activities.postActivities.LocationActivity;
import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.ui.view.PostView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * A fragment that display the post page
 */
public class PostFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, PostView {

    private final String TAG = "TAG_PostFragment";

    private Button departureLocation;
    private Button arrivalLocation;
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

    private String arrivalCity;
    private String locationAddress;

    //Data to get from the UI
    private String departureLocationAddress;
    private String arrivalLocationAddress;

    private int uiPrice;
    private int uiPassengers;


    private static final int REQUEST_CODE_GET_DEPARTURE_LOCATION= 1;
    private static final int REQUEST_CODE_GET_ARRIVAL_LOCATION= 2;

    public PostFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup groupView = (ViewGroup)inflater.inflate(R.layout.fragment_post, container, false);

        departureLocation = groupView.findViewById(R.id.departure_location);
        arrivalLocation = groupView.findViewById(R.id.arrival_location);
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

        departureLocation.setOnClickListener(this);
        arrivalLocation.setOnClickListener(this);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        //Potential Call from Presenter
        if(v == departureLocation){
            Intent departIntent = new Intent(getActivity(), LocationActivity.class);
            startActivityForResult(departIntent, REQUEST_CODE_GET_DEPARTURE_LOCATION);
        }
        else if(v == arrivalLocation){
            Intent arriveIntent = new Intent(getActivity(), LocationActivity.class);
            startActivityForResult(arriveIntent, REQUEST_CODE_GET_ARRIVAL_LOCATION);
        }
        else if(v == departureDateLabel || v ==  departureDate){
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
                        price.setText("$" + String.valueOf(picker.getValue()));
                        postRide.setEnabled(true);
                        uiPrice = picker.getValue();
                    })
                    .setNegativeButton("CANCEL", (dialog, which) -> {

                    });
            builder.setView(picker);
            builder.create().show();
        }else if(v == postRide){


             String departure_city= getCityAddress(departureLocationAddress);
             String departure_address= getStreetAddress(departureLocationAddress);
             String arrival_city= getCityAddress(arrivalLocationAddress);
             String arrival_address= getStreetAddress(arrivalLocationAddress);

             float fare = uiPrice;
             int available_spot = uiPassengers;

            String departure_datetime = getLocalDateTime();


            PostData postData=new PostData(departure_city, departure_address, arrival_city, arrival_address,
            fare, available_spot, departure_datetime);

            //Show Dialog

            this.progressDialog = new ProgressDialog(getActivity());

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

    private String getStreetAddress(String address) {

        StringTokenizer stringTokenizer = new StringTokenizer(address, ",");
        String output = "";


        while (stringTokenizer.hasMoreElements()) {
            if(stringTokenizer.countTokens() == 4) return stringTokenizer.nextElement().toString();

            String temp = stringTokenizer.nextElement().toString();
                StringTokenizer str = new StringTokenizer(temp, " ");

                if (str.countTokens() ==1)
                    output += temp + ", ";
                else
                    output += str.nextElement().toString() + ", ";
        }
        Log.d(TAG, output);

        return output;

    }

    private String getCityAddress(String address) {
        StringTokenizer stringTokenizer = new StringTokenizer(address, ",");
        String output = "";
        String  temp;

        int count = stringTokenizer.countTokens();

        if(count ==4)
            temp = stringTokenizer.nextElement().toString(); //do not delete this line

        while (stringTokenizer.hasMoreElements()) {
            temp = stringTokenizer.nextElement().toString();

            if(count != stringTokenizer.countTokens()){
                StringTokenizer str = new StringTokenizer(temp, " ");

                if (str.countTokens() ==1)
                    output += temp + ", ";
                else
                    output += str.nextElement().toString() + ", ";
            }
            else count -=1;
        }
        Log.d(TAG, output);
        return output;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (REQUEST_CODE_GET_DEPARTURE_LOCATION == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                Bundle locationInformation = data.getExtras();
                double departureLatitude = locationInformation.getDouble("locationLatitude");
                double departureLongitude = locationInformation.getDouble("locationLongitude");
                String departureCity = locationInformation.getString("locationCity");
                locationAddress = locationInformation.getString("locationAddress");
                departureLocation.setText(locationAddress);
                departureLocationAddress = locationAddress;
                Log.d("Arrival City--Dep", departureLocationAddress);

            }
        }else if(REQUEST_CODE_GET_ARRIVAL_LOCATION == requestCode){
            if (Activity.RESULT_OK == resultCode) {
                Bundle locationInformation = data.getExtras();
                double arrivalLatitude = locationInformation.getDouble("locationLatitude");
                double arrivalLongitude = locationInformation.getDouble("locationLongitude");
                arrivalCity = locationInformation.getString("locationCity");
                locationAddress = locationInformation.getString("locationAddress");
                arrivalLocation.setText(locationAddress);
                arrivalLocationAddress = locationAddress;
                Log.d("Arrival City--Ari", arrivalLocationAddress);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
    }

    public void hideLoading() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public void postSuccess(PostResponse post) {

    }

    @Override
    public void postFailure(ErrorCode code) {
        if (code.getId() == 7) {
            Toast.makeText(
                    getActivity(),
                    String.valueOf(R.string.activity_post_error),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void postFailure(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_LONG).show();
    }
}