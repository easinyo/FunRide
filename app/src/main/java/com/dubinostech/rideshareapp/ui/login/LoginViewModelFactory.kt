package com.dubinostech.rideshareapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dubinostech.rideshareapp.presenter.LoginPresenter
import com.dubinostech.rideshareapp.data.LoginRepository
import com.dubinostech.rideshareapp.model.loginModel.LoginViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    presenter = LoginPresenter()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
