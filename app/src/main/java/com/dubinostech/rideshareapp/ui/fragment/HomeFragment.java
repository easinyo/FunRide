package com.dubinostech.rideshareapp.ui.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.loginModel.SearchModel;
import com.dubinostech.rideshareapp.presenter.SearchRidePresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.activities.ReservationActivity;
import com.dubinostech.rideshareapp.ui.view.SearchListAdapter;
import com.dubinostech.rideshareapp.ui.view.SearchView;

import java.text.SimpleDateFormat;
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
    private EditText departure;
    private EditText arrival;
    private TextView date;
    private TextView error;
    private Calendar myCalendar;
    private ListView listview;
    private SearchListAdapter searchListAdapter;



    private static final int REQUEST_CODE_GET_DEPARTURE_LOCATION= 1;
    private static final int REQUEST_CODE_GET_ARRIVAL_LOCATION= 2;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup groupView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        departure = groupView.findViewById(R.id.departure);
        arrival = groupView.findViewById(R.id.arrival);
        searchRide = groupView.findViewById(R.id.search);
        date = groupView.findViewById(R.id.date);
        error = groupView.findViewById(R.id.error);
        myCalendar = Calendar.getInstance();

        listview = groupView.findViewById(R.id.search_list);

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
            progressDialog.setMessage("Please wait, We are searching for your ride...");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void searchSuccess(List<SearchResponse> rides) {
        progressDialog.dismiss();
        //        if(rides.size() > 0) {
//            final Intent intent = new Intent(getContext(), SearchResultActivity.class);
//            ArrayList<Integer> ridesList = new ArrayList<Integer>();
//            Object[] array = rides.toArray();
//            if (array == null) {
//                //error = "No trips found.";
//            } else {
//                for (int i = 0; i < array.length ; i++) {
//                    //For each integer, adds it to list
//                    ridesList.add((Integer) array[i]);
//                }
//                Bundle extras = new Bundle();
//                //extras.putString("EXTRA_USERNAME", eusername);
//                extras.putIntegerArrayList("rides", ridesList);
//                intent.putExtras(extras);
//                startActivity(intent);
//            }
//        }

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
                mBundle.putString("departure_date", rides.get(position).getDepartureDateTime());
                mBundle.putString("cost", rides.get(position).getFare());
                mBundle.putString("available_spots", rides.get(position).getDavailableSpot());
                intent.putExtras(mBundle);
                getActivity().startActivity(intent);
            }
        });
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
}
