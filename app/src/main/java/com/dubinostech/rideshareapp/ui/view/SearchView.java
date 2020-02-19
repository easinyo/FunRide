/**
Interface LoginView
To be implemented by LoginActivity
*/
package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.repository.Api.Responses.LoginResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.google.gson.JsonArray;

public interface SearchView {

    void showLoading();
    void hideLoading();
    void setSearchError(ErrorCode code);
    void searchSuccess(LoginResponse user);
    void searchFailure(ErrorCode code);
    void loginFailure(String errMsg);
    void searchResponse(JsonArray rides);
}
