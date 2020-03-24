package com.dubinostech.rideshareapp.repository.Libraries;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.util.Log;

import com.dubinostech.rideshareapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static final int  SIGNUP = 0; // This will be used to call sign up endpoint
    public static final int EDITPROFILE = 1; // this will be used to call update endpoint
    /**
     * check email validations.
     *
     * @param target
     * @return
     */
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    /**
     * Method is used for checking network availability.
     *
     * @param context
     * @return isNetAvailable: boolean true for Internet availability, false
     * otherwise
     */
    public static boolean isNetworkAvailable(Context context) {

        boolean isNetAvailable = false;
        if (context != null) {

            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {

                boolean mobileNetwork = false;
                boolean wifiNetwork = false;

                boolean mobileNetworkConnected = false;
                boolean wifiNetworkConnected = false;

                final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo != null) {

                    mobileNetwork = mobileInfo.isAvailable();
                }

                if (wifiInfo != null) {

                    wifiNetwork = wifiInfo.isAvailable();
                }

                if (wifiNetwork || mobileNetwork) {

                    if (mobileInfo != null) {

                        mobileNetworkConnected = mobileInfo.isConnectedOrConnecting();
                    }
                    wifiNetworkConnected = wifiInfo.isConnectedOrConnecting();
                }
                isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
            }
        }

        return isNetAvailable;
    }


    /**
     * Common AppCompat Alert Dialog to be used in the Application everywhere
     *
     * @param mContext, Context of where to display
     */
    public static void displayCommonAlertDialog(final Context mContext, final String alertMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.StyleAppCompatAlertDialog);
            builder.setTitle(mContext.getResources().getString(R.string.connection_dialog));
            builder.setMessage(alertMessage);
            builder.setPositiveButton(mContext.getResources().getString(R.string.activity_login_alert_ok), null);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @param alertMessage
     * display AlertDialog with Counter
     */
    public static void displayAlertDialogWithCounter(final Context context, final String alertMessage){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.StyleAppCompatAlertDialog)
                .setMessage(alertMessage);

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

    /**
     *
     * @param addressObj must be not null
     * @return the full adress id not null, else, the city Adress
     */
    public static String getFullAddressToString(Address addressObj) {
        String address = addressObj.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String postalCode = addressObj.getPostalCode();

        return (address!=null)? address : getCityAddressToString(addressObj) + ", " + postalCode;
    }

    /**
     *
     * @param addressObj must be not null
     * @return the full city, province or state, country, example: Ottawa, Ontario, Canada
     */
    public static String getCityAddressToString(Address addressObj) {
        String city = addressObj.getLocality();
        String state = addressObj.getAdminArea();
        String country = addressObj.getCountryName();

        return (addressObj==null)? null : city + ", " + state + ", " + country;
    }

    /**
     *
     * @param place
     * @param context
     * @return the address from place, example: Mirage hotel and casino, Las Vegas, NV 89101, US
     */
    public static Address placeToAddress(Place place, Context context){
        final LatLng latLng = place.getLatLng();

        Geocoder geocoder;
        List<Address> addresses;
        Address address = new Address(new Locale("en"));
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            address = (addresses.size() > 0)? addresses.get(0): address;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * @param myCalendar
     * @return the localDateTime
     */
    public static String getLocalDateTime(Calendar myCalendar){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",  Locale.US);
        String output = parser.format(myCalendar.getTime());

        Log.d("Date" + " LocalDateTime" , output);

        return output;
    }
}

