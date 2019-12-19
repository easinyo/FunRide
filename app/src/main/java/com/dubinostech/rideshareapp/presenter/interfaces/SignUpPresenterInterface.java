/**
* Interface SignUpPresenterInterface to organize the signup presenter
* To be implemented by SignupPresenter
* Has CallSignup method that takes an object user
*/
package com.dubinostech.rideshareapp.presenter.interfaces;

import com.dubinostech.rideshareapp.data.User;

public interface SignUpPresenterInterface {
    void callSignUp(User user);
}
