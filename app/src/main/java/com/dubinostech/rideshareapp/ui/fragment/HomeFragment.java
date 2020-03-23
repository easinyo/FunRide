package com.dubinostech.rideshareapp.ui.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.loginModel.SearchModel;
import com.dubinostech.rideshareapp.presenter.SearchRidePresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Libraries.Utils;
import com.dubinostech.rideshareapp.ui.activities.ReservationActivity;
import com.dubinostech.rideshareapp.ui.view.SearchListAdapter;
import com.dubinostech.rideshareapp.ui.view.SearchView;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * A fragment that display the post page
 */
public class HomeFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, SearchView {

    private Button searchRide;
    private TextView departureDateLabel;
    private TextView departureTimeLabel;
    private TextView date;
    private TextView error;
    private Calendar myCalendar;
    private ListView listview;

    private ProgressDialog progressDialog;

    PlacesClient placesClient;

    String departure_city;
    String arrival_city;

    final String API_KEY = "AIzaSyByniyl8kvVAZ0eGBbPxyUVzVg9jgV5XfA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup groupView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        searchRide = groupView.findViewById(R.id.search);
        departureDateLabel = groupView.findViewById(R.id.departure_date_label);
        departureTimeLabel = groupView.findViewById(R.id.departure_time_label);
        date = groupView.findViewById(R.id.date);
        error = groupView.findViewById(R.id.error);
        myCalendar = Calendar.getInstance();

        listview = groupView.findViewById(R.id.search_list);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));

        date.setOnClickListener(this);
        searchRide.setOnClickListener(this);

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), API_KEY);
        }
        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(getActivity());

        final AutocompleteSupportFragment autocompleteSupportFragmentArrival =
                (AutocompleteSupportFragment)
                        getChildFragmentManager().findFragmentById(R.id.autocomplete_arrival_location);
        autocompleteSupportFragmentArrival.getView().setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));


        autocompleteSupportFragmentArrival.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

        autocompleteSupportFragmentArrival.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        Address address = Utils.placeToAddress(place, getContext());

                        arrival_city = address.getLocality()+ ", " + address.getAdminArea() + ", " + address.getCountryName();

                    }
                    @Override
                    public void onError(Status status) {
                    }
                });

        final AutocompleteSupportFragment autocompleteSupportFragmentDeparture =
                (AutocompleteSupportFragment)
                        getChildFragmentManager().findFragmentById(R.id.autocomplete_departure_city);
        autocompleteSupportFragmentDeparture.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        autocompleteSupportFragmentDeparture.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

        autocompleteSupportFragmentDeparture.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        Address address = Utils.placeToAddress(place, getContext());

                        departure_city = address.getLocality()+ ", " + address.getAdminArea() + ", " + address.getCountryName();

                    }

                    @Override
                    public void onError(Status status) {
                    }
                });

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

        listview.setAdapter(null);
        error.setVisibility(View.INVISIBLE);

        String dateStr = date.getText().toString();

        this.progressDialog = new ProgressDialog(getActivity());

        if (departure_city.isEmpty()) {
            displayToast("Departure city field empty");
        } else if (arrival_city.isEmpty()) {
            displayToast("Arrival city field empty");
        } else if (dateStr.isEmpty()) {
            displayToast("Date field empty");
        } else {
            SearchRidePresenter presenter = new SearchRidePresenter(this, new SearchModel());
            presenter.callSearch(departure_city, arrival_city, dateStr);
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
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void showLoading() {
        if (progressDialog != null)
            progressDialog.setMessage("Please wait, We are searching for your ride...");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void searchSuccess(List<SearchResponse> rides) {
         hideLoading();
         if(rides==null || rides.size()==0){
            error.setText("Sorry, no rides found!");
            error.setVisibility(View.VISIBLE);
         }
        SearchListAdapter adapter = new SearchListAdapter(getContext(), 0, rides);

        listview.setAdapter(adapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                Bundle mBundle = new Bundle();

                mBundle.putString("tripID", rides.get(position).getId());
                mBundle.putString("departure_city", rides.get(position).getDepartureCity());
                mBundle.putString("arrival_city", rides.get(position).getArrivalCity());
                mBundle.putString("departure_address", rides.get(position).getDepartureAddress());
                mBundle.putString("arrival_address", rides.get(position).getArrivalAddress());
                mBundle.putString("departure_date", rides.get(position).getDepartureDate());
                mBundle.putString("departure_time", rides.get(position).getDepartureTime());
                mBundle.putString("cost", rides.get(position).getFare());
                mBundle.putString("available_spots", rides.get(position).getDavailableSpot());
                intent.putExtras(mBundle);

                if (Integer.parseInt(rides.get(position).getDavailableSpot()) > 0)
                    getActivity().startActivity(intent);
                else
                    Utils.displayAlertDialogWithCounter(getContext(), " All Booked, please try another trip ");

        }});
    }

    @Override
    public void searchFailure(String errMsg) {
        hideLoading();
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchFailure(ErrorCode error) {
        hideLoading();
        if (error.getId() == 0) {
            Toast.makeText(
                    getActivity(),
                    "Error to display",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
