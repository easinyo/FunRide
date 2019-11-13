package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.data.User;


public class SignupPresenter {

    public void register(String firstnameStr, String lastnameStr, String emailStr, String phoneStr, String passwordStr, String confirmPasswordStr) {
        User u = new User();
        u.setFirstName(firstnameStr);
        u.setLastName(lastnameStr);
        u.setEmail(emailStr);
        u.setPhone(phoneStr);
        u.setPassword(passwordStr);
        u.setConfirmPassword(confirmPasswordStr);
    }
}
