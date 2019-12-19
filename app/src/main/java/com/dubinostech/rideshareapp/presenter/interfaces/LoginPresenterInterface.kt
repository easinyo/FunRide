/**
* Interface LoginPresenterInterface to organize the login presenter
* To be implemented by LoginPresenter
* Has CallLogin method that takes username and password
*/
package com.dubinostech.rideshareapp.presenter.interfaces

interface LoginPresenterInterface {

    fun callLogin(userName: String, passWord: String)

}
