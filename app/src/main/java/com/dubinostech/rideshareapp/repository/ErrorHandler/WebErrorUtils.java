package com.dubinostech.rideshareapp.repository.ErrorHandler;

import com.dubinostech.rideshareapp.repository.Api.GatewayAPI;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class WebErrorUtils {

        public static ApIError parseError(Response<?> response) {
            GatewayAPI gatewayAPI = new GatewayAPI(null);
            Converter<ResponseBody, ApIError> converter =
                    gatewayAPI.getRetrofit()
                            .responseBodyConverter(ApIError.class, new Annotation[0]);

            ApIError error = new ApIError();
            error.setStatus_code(010);

            try {
//            Log.e("ErrorUtils", "Service Error Body : " + response.body().toString());
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                ApIError networkError = new ApIError();
                if (response.code() == 404) {
                    networkError.setStatus_code(404);
                    networkError.setMessage("Page Not Found");
                    return networkError;
                } else if (response.code() == 403) {
                    networkError.setStatus_code(403);
                    networkError.setMessage("Problem while fetching data from server. Please try again later.");
                    return networkError;
                } else if (e.getCause() instanceof SocketTimeoutException) {
                    networkError.setStatus_code(000);
                    networkError.setMessage("Network Error! \\nPoor connection or connection lost.");
                    return networkError;
                } else if (e.getCause() instanceof UnknownHostException || e.getCause() instanceof ConnectException) {
                    networkError.setStatus_code(000);
                    networkError.setMessage("No internet connection.");
                    return networkError;
                } else {
                    networkError.setStatus_code(000);
                    networkError.setMessage("Problem while fetching data from server. Please try again later.");
                    return networkError;
                }
            }

            return error;
        }
    }


