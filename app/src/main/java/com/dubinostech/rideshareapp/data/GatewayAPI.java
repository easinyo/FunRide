package com.dubinostech.rideshareapp.data;

import com.dubinostech.rideshareapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


}
