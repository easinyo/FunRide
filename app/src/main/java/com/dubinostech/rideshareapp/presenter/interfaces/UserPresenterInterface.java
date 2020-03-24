/**
* Interface UserPresenterInterface to organize the signup presenter
* To be implemented by UserPresenter
* Has CallSignup method that takes an object user
*/
package com.dubinostech.rideshareapp.presenter.interfaces;

import com.dubinostech.rideshareapp.repository.Data.User;

public interface UserPresenterInterface {
    void callUserSignUpOrUpdate(User user, int code);
}
