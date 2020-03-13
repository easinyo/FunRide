/**
Interface LoginView
To be implemented by LoginActivity
*/
package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;

import java.util.List;

public interface SearchView {

    void showLoading();
    void hideLoading();

    void searchSuccess(List<SearchResponse> trip);

    void searchFailure(String errMsg);

    void searchFailure(ErrorCode error);
    //void searchResponse(JsonArray rides);
}
