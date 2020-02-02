package com.dubinostech.rideshareapp.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dubinostech.rideshareapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A fragment that display the post page
 */
public class HomeFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button searchRide;
    private EditText departure;
    private EditText arrival;
    private TextView date;
    private Calendar myCalendar;


    private static final int REQUEST_CODE_GET_DEPARTURE_LOCATION= 1;
    private static final int REQUEST_CODE_GET_ARRIVAL_LOCATION= 2;


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
        searchRide.setEnabled(false);

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

            // Send Data as a Search Ride request to backend
        }
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

}
