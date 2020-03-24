package com.dubinostech.rideshareapp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.repository.Data.LoggedUser;
import com.dubinostech.rideshareapp.ui.activities.ContactActivity;
import com.dubinostech.rideshareapp.ui.activities.LoginActivity;
import com.dubinostech.rideshareapp.ui.view.Terms;

public class ProfileFragment extends Fragment {

    LinearLayout personalinfo, experience, review, completedTrips, cancelledtrips, postedTrips, terms, contactUs, rate;
    TextView personalinfobtn, experiencebtn, reviewbtn, tripsDone, name, email, phoneNumber, location;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        /*User Info*/
        name = rootView.findViewById(R.id.name);
        email = rootView.findViewById(R.id.email);
        phoneNumber = rootView.findViewById(R.id.phoneNumber);
        location = rootView.findViewById(R.id.location);
        tripsDone = rootView.findViewById(R.id.tripsDone);

        /*Trips Layouts*/
        completedTrips = rootView.findViewById(R.id.personalinfo);
        cancelledtrips = rootView.findViewById(R.id.personalinfo);
        postedTrips = rootView.findViewById(R.id.personalinfo);

        /*Settings Layouts*/
        terms = rootView.findViewById(R.id.terms);
        contactUs = rootView.findViewById(R.id.contactUs);
        rate = rootView.findViewById(R.id.rate);

        /*buttons*/
        logout = rootView.findViewById(R.id.logout);

        /*Layouts*/
        personalinfo = rootView.findViewById(R.id.personalinfo);
        experience = rootView.findViewById(R.id.experience);
        review = rootView.findViewById(R.id.review);
        personalinfobtn = rootView.findViewById(R.id.personalinfobtn);
        experiencebtn = rootView.findViewById(R.id.experiencebtn);
        reviewbtn = rootView.findViewById(R.id.reviewbtn);

        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.GONE);
        review.setVisibility(View.GONE);

        /*Setup onclick*/
        completedTrips.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("My completed Trips")
                    .setMessage("This feature will be coming up soon.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        postedTrips.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("My posted Trips")
                    .setMessage("This feature will be coming up soon.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        cancelledtrips.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("My cancelled Trips")
                    .setMessage("This feature will be coming up soon.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        terms.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Terms.class);
            startActivity(intent);
        });

        contactUs.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContactActivity.class);
            startActivity(intent);
        });

        rate.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Rate a driver")
                    .setMessage("This feature will be coming up soon.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        logout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        /*Bar layouts*/
        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
                reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

        experiencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.VISIBLE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                experiencebtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.VISIBLE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
                reviewbtn.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        name.setText(LoggedUser.name);
        email.setText(LoggedUser.email);
        phoneNumber.setText(LoggedUser.phone_number);
    }
}
