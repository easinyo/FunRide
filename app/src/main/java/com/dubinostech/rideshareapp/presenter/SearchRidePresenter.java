/**
 * The class LoginPresenter is the presenter of the login view. 
 * It implements the interface LoginPresenterInterface
 * It has a method callLogin which is called by the view when an existing user tries to login. 
 * It then updates login model and view.
 */
package com.dubinostech.rideshareapp.presenter;

/*
public class SearchRidePresenter implements SearchRideInterface {



    SearchView searchView;
    LogInCallback logInCallback;

    public SearchRidePresenter(SearchView searchView, LogInCallback logInCallback) {
        this.searchView = searchView;
        this.logInCallback = logInCallback;
    }

    /**
     * callLogin is a helper method that handles the user login
     * @param departure
     *            String representing the user's username or email
     * @param arrival
     *            String representing the user's password
     * It verifies the constraints for loging in and sets the Errors if needed
     * Then notifies the login view
     */

    /*
    @Override
    public void callSearch(String departure, String arrival, String date) {
        SearchView.showLoading();
        logInCallback.login(departure, arrival, new LoginInCallback.IValidationErrorListener() {
            @Override public void searchError(ErrorCode code) {
                SearchView.hideLoading();
                SearchView.setSearchError(code);
            }

        }, new LogInCallback.IOnLoginFinishedListener() {
            @Override public void getUserData(LoginResponse user) {
                SearchView.hideLoading();
                if (user != null) {
                    SearchView.searchSuccess(user);
                } else {
                    SearchView.searchFailure(ErrorCode.LOGIN_FAILED);
                }
            }

            @Override public void errorMsg(String errorMsg) {
                SearchView.hideLoading();
                SearchView.loginFailure(errorMsg);
            }

        });

    }
*/
