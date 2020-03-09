package com.dubinostech.rideshareapp.repository.Api;

/* *
* Class GatewayAPI
*
*This class is our Gateway where we communicate with our API
*
* Gateway constructor takes Interceptor
*
* */

import com.dubinostech.rideshareapp.BuildConfig;
import com.dubinostech.rideshareapp.repository.Api.Raws.LoginRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.PostRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.SearchRaw;
import com.dubinostech.rideshareapp.repository.Api.Raws.SignupRaw;
import com.dubinostech.rideshareapp.repository.Api.Responses.LoginResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.Api.Responses.SignupResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A gateway API that enables connection between front end and backend through the https request
 */
public class GatewayAPI {

    private static final String TAG = "GatewayAPI";
    private GatewayAPIInterface service;
    private Retrofit retrofit;
    private Interceptor mHeaderInterceptor;

    public static final String WS_URL = "https://fun-ride.herokuapp.com/";


    public GatewayAPI(Interceptor headerInterceptor) {
        mHeaderInterceptor = headerInterceptor;
        retrofit = getRetrofit();
        service = retrofit.create(GatewayAPIInterface.class);
    }

    public Retrofit getRetrofit() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client;

        if (mHeaderInterceptor != null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(mHeaderInterceptor)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG)
                builder.addInterceptor(interceptor);
            client = builder.build();
        } else {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG)
                builder.addInterceptor(interceptor);
            client = builder.build();
        }

        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WS_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Call<LoginResponse> login(LoginRaw raw) {
        return service.login(raw);
    }

    public Call<SignupResponse> signup(SignupRaw raw) {
        return service.signup(raw);
    }

    public Call<PostResponse> postRide(PostRaw raw) {
        return service.postRide(raw);
    }

    public Call<SearchResponse> search(SearchRaw raw) {
        return service.search(raw);
    }

}
