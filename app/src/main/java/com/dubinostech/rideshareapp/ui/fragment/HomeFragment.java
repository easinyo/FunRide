package com.dubinostech.rideshareapp.ui.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.loginModel.SearchModel;
import com.dubinostech.rideshareapp.presenter.SearchRidePresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.view.SearchView;
import com.google.gson.JsonArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * A fragment that display the post page
 */
public class HomeFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, SearchView {

    private Button searchRide;
    private EditText departure;
    private EditText arrival;
    private TextView date;
    private Calendar myCalendar;


    private static final int REQUEST_CODE_GET_DEPARTURE_LOCATION= 1;
    private static final int REQUEST_CODE_GET_ARRIVAL_LOCATION= 2;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup groupView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        departure = groupView.findViewById(R.id.departure_city);
        arrival = groupView.findViewById(R.id.arrival_city);
        searchRide = groupView.findViewById(R.id.search_ride);
        date = groupView.findViewById(R.id.departure_date);
        myCalendar = Calendar.getInstance();

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));

        date.setOnClickListener(this);
        searchRide.setOnClickListener(this);
        searchRide.setEnabled(true);

        return groupView;
    }


    @Override
    public void onClick(View v) {
        if(v == date ){
            DatePickerDialog dialog =  new DatePickerDialog(getContext(), this, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if(v == searchRide){
            searchRide();
        }
    }

    private void searchRide() {
        String departureStr = departure.getText().toString();
        String arrivalStr = arrival.getText().toString();
        String dateStr = date.getText().toString();

        this.progressDialog = new ProgressDialog(getActivity());

        if (departureStr.isEmpty()) {
            displayToast("Departure city field empty");
        } else if (arrivalStr.isEmpty()) {
            displayToast("Arrival city field empty");
        } else if (dateStr.isEmpty()) {
            displayToast("Date field empty");
        } else {
            SearchRidePresenter presenter = new SearchRidePresenter(this, new SearchModel());
            presenter.callSearch(departureStr, arrivalStr, dateStr);
        }
    }

    private void displayToast(String message) {
        makeText(getActivity(), message, LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Log.d("Date", String.valueOf(myCalendar.getTime()));
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void showLoading() {
        if (progressDialog != null)
            progressDialog.setTitle("Searching for rides");
        progressDialog.setMessage(String.valueOf(R.string.activity_search_loading_msg));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void searchSuccess(SearchResponse trip) {

    }

    @Override
    public void searchFailure(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchFailure(ErrorCode error) {
        if (error.getId() == 0) {
            Toast.makeText(
                    getActivity(),
                    "Error to display",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void searchResponse(JsonArray rides) {

    }
}
