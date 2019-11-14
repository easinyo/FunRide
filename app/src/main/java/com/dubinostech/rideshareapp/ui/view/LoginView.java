package com.dubinostech.rideshareapp.ui.view;

import com.dubinostech.rideshareapp.data.LoggedInUser;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void showConnectionErrorMessage();
    void showError(String message);
    void onLoginSuccess(LoggedInUser loggedInUser);
}
