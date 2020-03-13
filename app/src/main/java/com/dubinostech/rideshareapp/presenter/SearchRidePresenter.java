/**
 * The class LoginPresenter is the presenter of the login view. 
 * It implements the interface LoginPresenterInterface
 * It has a method callLogin which is called by the view when an existing user tries to login. 
 * It then updates login model and view.
 */
package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.model.loginModel.SearchCallBack;
import com.dubinostech.rideshareapp.model.loginModel.SearchCallBack.IOnSearchFinishedListener;
import com.dubinostech.rideshareapp.presenter.interfaces.SearchRideInterface;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.view.SearchView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchRidePresenter implements SearchRideInterface {


    SearchView searchView;
    SearchCallBack searchCallback;

    public SearchRidePresenter(SearchView searchView, SearchCallBack searchCallback) {
        this.searchView = searchView;
        this.searchCallback = searchCallback;
    }

    /*
     * callLogin is a helper method that handles the user login
     * @param departure
     *            String representing the user's username or email
     * @param arrival
     *            String representing the user's password
     * It verifies the constraints for loging in and sets the Errors if needed
     * Then notifies the login view
     */

    @Override
    public void callSearch(String departure, String arrival, String date) {
        searchView.showLoading();
        searchCallback.search(departure, arrival, date, new IOnSearchFinishedListener() {
            @Override
            public void getTripData(@NotNull List<SearchResponse> trips) {
                searchView.hideLoading();
                if (trips != null) {
                    searchView.searchSuccess(trips);
                } else {
                    searchView.searchFailure(ErrorCode.SEARCH_FAILED);
                }
            }

            @Override
            public void errorMsg(String errorMsg) {
                searchView.hideLoading();
                searchView.searchFailure(errorMsg);
            }

        });

    }
}

