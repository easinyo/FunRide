/**
Interface LoginView
To be implemented by LoginActivity
*/
package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.google.gson.JsonArray;

public interface SearchView {

    void showLoading();
    void hideLoading();

    void searchSuccess(SearchResponse trip);

    void searchFailure(String errMsg);

    void searchFailure(ErrorCode error);
    void searchResponse(JsonArray rides);
}
