package com.dubinostech.rideshareapp.data;

import com.dubinostech.rideshareapp.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

public class GatewayAPI {

    private static final String TAG = "GatewayAPI";
    private static ServicesApiInterface servicesApiInterface;

    public static ServicesApiInterface getMyApiClient() {

        if (servicesApiInterface == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.HOST_URL)
                    .setClient(new OkClient(getClient()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            servicesApiInterface = restAdapter.create(ServicesApiInterface.class);
        }
        return servicesApiInterface;
    }

    public interface ServicesApiInterface {

        ///<version name>/users/login
        //@Headers({"Content-Type: application/json"})


        @Headers({
                "Content-Type: application/json",
                "application-id: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx",
                "secret-key: xxxxxxx-xxxx-xxxx-xxxx-xxxxxxxx",
                "application-type: REST"
        })

        @POST("/v1/users/login")
        void login(@Body LoginRaw raw, Callback<LoginResponse> callback);
    }

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(2, TimeUnit.MINUTES);
        client.setReadTimeout(2, TimeUnit.MINUTES);
        return client;
    }
}
