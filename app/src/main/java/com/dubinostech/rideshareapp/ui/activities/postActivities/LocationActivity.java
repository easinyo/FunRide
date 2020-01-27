package com.dubinostech.rideshareapp.ui.activities.postActivities;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.repository.Libraries.SimpleAddressAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * An activity class that will allow the user to enter specific adress and select it for Arrival or departure.
 */

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    private Toolbar toolbar;
    private Button setLocation;
    private GoogleMap mMap;
    private EditText mSearchEditText;
    private PopupWindow addressWindow;

    private double locationLongitude;
    private double locationLatitude;

    private String locationCity;
    private String locationAddress;

    private Address location;
    private SimpleAddressAdapter adapter;

    private static final int ERROR_DIALOG_REQUEST = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //toolbar = findViewById(R.id.options_toolbar);
        setLocation = findViewById(R.id.set_location);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/
    }
    /**
     * a function that will set the location selected
     * @param v
     */
    public void setLocation(View v){
        if (location != null)
            locationLatitude = location.getLatitude();
            locationLongitude = location.getLongitude();
            locationCity = location.getLocality();
            Log.d("locationCity", locationCity);
            locationAddress = location.getAddressLine(0);


            Intent resultIntent = new Intent();
            resultIntent.putExtra("locationLatitude", locationLatitude);
            resultIntent.putExtra("locationLongitude", locationLongitude);
            resultIntent.putExtra("locationCity", locationCity);
            resultIntent.putExtra("locationAddress", locationAddress);

            setResult(RESULT_OK, resultIntent);
            finish();
    }
    /**
     * a function that will allow the user to search for adress
     * @param view
     */
    public void onSearchButtonClicked(View view){
        Geocoder departureAddress = new Geocoder(this);
        String address = mSearchEditText.getText().toString();
        List<Address> addresses;

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.search_address_pop_up, findViewById(R.id.address_list_pop_up_menu));
        final ListView listView = layout.findViewById(R.id.address_list);

        addressWindow= new PopupWindow(layout, 1000, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        addressWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        try {
            addresses = departureAddress.getFromLocationName(address, 10);
            adapter = new SimpleAddressAdapter(addresses, this);


            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                location = (Address) listView.getAdapter().getItem(position);
                addressWindow.dismiss();
                handleNewLocation(location.getLatitude(), location.getLongitude());
            });
            // location = addresses.get(0);

        }catch(Exception e){
            Log.d("POST ACTIVITY",e.getLocalizedMessage());
        }

    }
    /**
     * a function that will give location option to the user to select
     * @param lat
     * @param lng
     * @param zoom
     */
    private void gotoLocation(double lat,double lng,float zoom) {
        LatLng latLng=new LatLng(lat,lng);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }

    /**
     * a function that Checks the google play services is available
    */
    private boolean checkServices() {
        //returns a integer value
        int isAvailable= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        //if connection is available
        if (isAvailable== ConnectionResult.SUCCESS){
            return true;
        }else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
            Dialog dialog= GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "Cannot connnect to mapping Service", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);

        View searchRef = menu.findItem(R.id.action_search).getActionView();
        mSearchEditText = searchRef.findViewById(R.id.searchText);

        mSearchEditText.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                onSearchButtonClicked(mSearchEditText);
                return true;
            }
            return false;
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }else if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * a function that initiates the map services
     */
    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return (mMap!=null);
    }
    /**
     * a function that handles new  location
     * @param latitudeValue
     * @param longitudeValue
     */
    private void handleNewLocation(double latitudeValue, double longitudeValue) {
        //getting the latitude value
        if (checkServices()) {
            if (initMap()) {
                //update the map with the current location
                gotoLocation(latitudeValue, longitudeValue, 15);

                // Other supported types include: MAP_TYPE_NORMAL,
                // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setMyLocationEnabled(true);

                //Setting up the marker
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(latitudeValue, longitudeValue))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.addMarker(marker);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        handleNewLocation(0, 0);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("GettingLocation", "onConnectionFailed");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("GettingLocation", "onConnectionFailed");
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}

